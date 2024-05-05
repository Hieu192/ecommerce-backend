package com.example.thuongmaidientu.service;

import com.example.thuongmaidientu.exception.CartItemException;
import com.example.thuongmaidientu.exception.ProductException;
import com.example.thuongmaidientu.exception.UserException;
import com.example.thuongmaidientu.model.Cart;
import com.example.thuongmaidientu.model.CartItem;
import com.example.thuongmaidientu.model.Product;
import com.example.thuongmaidientu.model.User;
import com.example.thuongmaidientu.repository.CartItemRepository;
import com.example.thuongmaidientu.repository.CartRepository;
import com.example.thuongmaidientu.request.AddItemRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemServiceImplementation implements CartItemService{
   private CartItemRepository cartItemRepository;
   private UserService userService;
   private CartRepository cartRepository;

    public CartItemServiceImplementation(CartItemRepository cartItemRepository, UserService userService, CartRepository cartRepository) {
        this.cartItemRepository = cartItemRepository;
        this.userService = userService;
        this.cartRepository = cartRepository;
    }


    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setQuantity(1);
        cartItem.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());

        CartItem createCartItem = cartItemRepository.save(cartItem);
        return createCartItem;
    }

    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {
        CartItem item = findCartItemById(id);
        User user = userService.findUserById(item.getUserId());
        if(user.getId().equals(userId)) {
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(item.getQuantity()*item.getProduct().getPrice());
            item.setDiscountedPrice(item.getProduct().getDiscountedPrice()*item.getQuantity());
        }
        return cartItemRepository.save(item);
    }

    @Override
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId) {
        CartItem cartItem = cartItemRepository.isCartItemExist(cart, product, size, userId);
        return cartItem;
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
        CartItem cartItem = findCartItemById(cartItemId);
        User user = userService.findUserById(cartItem.getUserId());

        User reqUser = userService.findUserById(userId);

        if(user.getId().equals(reqUser.getId())) {
            cartItemRepository.deleteById(cartItemId);
        } else {
            throw new UserException("bạn không thể xóa sản phẩm người dùng khác");
        }
    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {
        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);
        if(cartItem.isPresent()) {
            return cartItem.get();
        }
        throw new CartItemException("cartItem không được tìm thấy bới id: " + cartItemId);
    }
}
