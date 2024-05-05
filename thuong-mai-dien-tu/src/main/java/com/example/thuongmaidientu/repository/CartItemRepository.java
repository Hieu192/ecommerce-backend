package com.example.thuongmaidientu.repository;

import com.example.thuongmaidientu.model.Cart;
import com.example.thuongmaidientu.model.CartItem;
import com.example.thuongmaidientu.model.Product;
import com.example.thuongmaidientu.model.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("SELECT ci FROM CartItem ci WHERE ci.cart=:cart And ci.product=:product And ci.size=:size And ci.userId=:userId")
    public CartItem isCartItemExist(@Param("cart")Cart cart, @Param("product")Product product, @Param("size")String size, @Param("userId")Long userId);
}
