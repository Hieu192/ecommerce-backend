package com.example.thuongmaidientu.service;

import com.example.thuongmaidientu.exception.OrderException;
import com.example.thuongmaidientu.model.*;
import com.example.thuongmaidientu.repository.*;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImplementation implements OrderService{
    private CartRepository cartRepository;
    private CartService cartService;
    private ProductService productService;
    private OrderRepository orderRepository;

    private AddressRepository addressRepository;
    private UserRepository userRepository;
    private OrderItemService orderItemService;
    private OrderItemRepository orderItemRepository;

    public OrderServiceImplementation(CartRepository cartRepository, CartService cartService, ProductService productService, OrderRepository orderRepository, AddressRepository addressRepository, UserRepository userRepository, OrderItemService orderItemService, OrderItemRepository orderItemRepository) {
        this.cartRepository = cartRepository;
        this.cartService = cartService;
        this.productService = productService;
        this.orderRepository = orderRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
        this.orderItemService = orderItemService;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public Order createOrder(User user, Address shippingAddress) {
        shippingAddress.setUser(user);
        Address address = addressRepository.save(shippingAddress);
        user.getAddress().add(address);
        userRepository.save(user);

        Cart cart = cartService.findUserCart(user.getId());
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem: cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();

            orderItem.setPrice(cartItem.getPrice());
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setSize(cartItem.getSize());
            orderItem.setUserId(cartItem.getUserId());
            orderItem.setDiscountedPrice(cartItem.getDiscountedPrice());

            OrderItem createOrderItem = orderItemRepository.save(orderItem);

            orderItems.add(createOrderItem);
        }
        Order createOrder = new Order();
        createOrder.setUser(user);
        createOrder.setOrderItems(orderItems);
        createOrder.setTotalPrice(cart.getTotalPrice());
        createOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
        createOrder.setDiscounte(cart.getDiscounte());
        createOrder.setTotalItem(cart.getTotalItem());

        createOrder.setShippingAddress(address);
        createOrder.setOrderDate(LocalDateTime.now());
        createOrder.setOrderStatus("PENDING");
        createOrder.getPaymentDetails().setStatus("PENDING");
        createOrder.setLocalDateTime(LocalDateTime.now());

        Order savedOrder = orderRepository.save(createOrder);

        for(OrderItem orderItem: orderItems) {
            orderItem.setOrder(savedOrder);
            orderItemRepository.save(orderItem);
        }

        return savedOrder;
    }

    @Override
    public Order findOrderById(Long orderId) throws OrderException {
        Optional<Order> opt = orderRepository.findById(orderId);

        if(opt.isPresent()) {
            return opt.get();
        }
        throw new OrderException("order không tồn tại id " + orderId);
    }

    @Override
    public List<Order> usersOrderHistory(Long userId) {
        List<Order> orders= orderRepository.getUsersOrders(userId);
        return orders;
    }

    @Override
    public Order placedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("PLACED");
        order.getPaymentDetails().setStatus("COMPLETED");

        return order;
    }

    @Override
    public Order confirmedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("CONFIRMED");
        return orderRepository.save(order);
    }

    @Override
    public Order shippedOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("SHIPPED");
        return orderRepository.save(order);
    }

    @Override
    public Order deliveredOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("DELIVERED");
        return orderRepository.save(order);
    }

    @Override
    public Order cancledOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);
        order.setOrderStatus("CANCELED");
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {

        return orderRepository.findAll();
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {
        Order order = findOrderById(orderId);

        orderRepository.deleteById(orderId);
    }
}
