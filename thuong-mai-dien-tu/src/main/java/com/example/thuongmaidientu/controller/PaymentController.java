package com.example.thuongmaidientu.controller;

import com.example.thuongmaidientu.exception.OrderException;
import com.example.thuongmaidientu.model.Order;
import com.example.thuongmaidientu.repository.OrderRepository;
import com.example.thuongmaidientu.service.OrderService;
import com.example.thuongmaidientu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PaymentController {
    @Value("{vnpay.api.key}")
    String apiKey;

    @Value("{vnpay.api.secret")
    String apiSecret;

    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @Autowired
    private OrderRepository orderRepository;

//    @PostMapping("/payments/{orderId}")
//    public ResponseEntity<PaymentLinkResponse> createPaymentLink(@PathVariable Long orderId, @RequestHeader("Authorization")String jwt) throws OrderException {
//        Order order = orderService.findOrderById(orderId);
//    }
}
