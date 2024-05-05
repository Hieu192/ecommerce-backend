package com.example.thuongmaidientu.controller;

import com.example.thuongmaidientu.exception.CartItemException;
import com.example.thuongmaidientu.exception.UserException;
import com.example.thuongmaidientu.model.CartItem;
import com.example.thuongmaidientu.model.User;
import com.example.thuongmaidientu.reponse.ApiResponse;
import com.example.thuongmaidientu.service.CartItemService;
import com.example.thuongmaidientu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/cart_items")
public class CartItemController {
    @Autowired
    private UserService userService;
    @Autowired
    private CartItemService cartItemService;

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(@PathVariable Long cartItemId, @RequestHeader("Authorization")String jwt) throws UserException, CartItemException {
        User user = userService.findUserProfileByJwt(jwt);
        cartItemService.removeCartItem(user.getId(), cartItemId);

        ApiResponse res = new ApiResponse();
        res.setMessage("đã xóa cart item");
        res.setStatus(true);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }
    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItem(@RequestHeader("Authorization")String jwt, @PathVariable Long cartItemId, @RequestBody CartItem cartItem) throws UserException, CartItemException {
        User user = userService.findUserProfileByJwt(jwt);
        CartItem updateCartItem = cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);

        return new ResponseEntity<>(updateCartItem, HttpStatus.OK);
    }
}
