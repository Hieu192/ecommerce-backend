package com.example.thuongmaidientu.controller;

import com.example.thuongmaidientu.exception.OrderException;
import com.example.thuongmaidientu.model.Order;
import com.example.thuongmaidientu.reponse.ApiResponse;
import com.example.thuongmaidientu.repository.OrderRepository;
import com.example.thuongmaidientu.service.OrderService;
import com.example.thuongmaidientu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PaymentController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @Autowired
    private OrderRepository orderRepository;


    @GetMapping("/payments")
    public ResponseEntity<ApiResponse> redirect(@RequestParam("order_id") Long orderId, @RequestHeader("Authorization")String jwt) throws OrderException {
        Order order = orderService.findOrderById(orderId);
//                order.getPaymentDetails().setPaymentId(paymentId);
            order.getPaymentDetails().setStatus("COMPLETED");
            order.setOrderStatus("PLACED");
            System.out.println(order.getPaymentDetails().getStatus() + "payment status ");
            orderRepository.save(order);

            ApiResponse res = new ApiResponse("your order get placed", true);
            return new ResponseEntity<ApiResponse>(res, HttpStatus.OK);
    }
}
