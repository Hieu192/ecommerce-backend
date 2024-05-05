package com.example.thuongmaidientu.service;

import com.example.thuongmaidientu.exception.CartItemException;
import com.example.thuongmaidientu.exception.UserException;
import com.example.thuongmaidientu.model.Cart;
import com.example.thuongmaidientu.model.CartItem;
import com.example.thuongmaidientu.model.Product;

public interface CartItemService {
    public CartItem createCartItem(CartItem cartItem);
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException;
    public CartItem isCartItemExist(Cart cart, Product product, String size, Long userId);
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException;
    public CartItem findCartItemById(Long cartItemId) throws CartItemException;
}
