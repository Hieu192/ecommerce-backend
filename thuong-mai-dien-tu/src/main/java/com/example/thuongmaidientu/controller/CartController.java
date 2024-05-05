package com.example.thuongmaidientu.controller;

import com.example.thuongmaidientu.exception.ProductException;
import com.example.thuongmaidientu.exception.UserException;
import com.example.thuongmaidientu.model.Cart;
import com.example.thuongmaidientu.model.User;
import com.example.thuongmaidientu.reponse.ApiResponse;
import com.example.thuongmaidientu.request.AddItemRequest;
import com.example.thuongmaidientu.service.CartService;
import com.example.thuongmaidientu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
//@Tag(name="Cart Management", description=" find user cart, add item to cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @GetMapping("/")
//    @Operation(description = "find cart by user id")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization")String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.findUserCart(user.getId());

        return new ResponseEntity<Cart>(cart, HttpStatus.OK);
    }

    @PutMapping("/add")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemRequest req, @RequestHeader("Authorization")String jwt) throws UserException, ProductException{
        User user=userService.findUserProfileByJwt(jwt);
        cartService.addCartItem(user.getId(), req);

        ApiResponse res = new ApiResponse();
        res.setMessage("item cart đã thêm thành công");
        res.setStatus(true);
        return new ResponseEntity<>(res, HttpStatus.OK);

    }
}
