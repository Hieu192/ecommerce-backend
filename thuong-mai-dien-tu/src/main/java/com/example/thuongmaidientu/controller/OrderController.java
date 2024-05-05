package com.example.thuongmaidientu.controller;

import com.example.thuongmaidientu.exception.OrderException;
import com.example.thuongmaidientu.exception.UserException;
import com.example.thuongmaidientu.model.Address;
import com.example.thuongmaidientu.model.Cart;
import com.example.thuongmaidientu.model.Order;
import com.example.thuongmaidientu.model.User;
import com.example.thuongmaidientu.service.OrderService;
import com.example.thuongmaidientu.service.UserService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<Order> createOrder(@RequestBody Address shippingAddress, @RequestHeader("Authorization")String jwt) throws UserException{
        User user = userService.findUserProfileByJwt(jwt);

        Order order = orderService.createOrder(user, shippingAddress);

        return new ResponseEntity<Order>(order, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> userOrderHistory(@RequestHeader("Authorization")String jwt) throws UserException{
        User user= userService.findUserProfileByJwt(jwt);
        List<Order> orders = orderService.usersOrderHistory(user.getId());

        return new ResponseEntity<>(orders, HttpStatus.CREATED);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<Order> findOrderById(@RequestHeader("Authorization")String jwt, @PathVariable("Id") Long orderId) throws UserException, OrderException {
        User user= userService.findUserProfileByJwt(jwt);
        Order order = orderService.findOrderById(orderId);

        return new ResponseEntity<>(order, HttpStatus.ACCEPTED);
    }
}
