package com.example.thuongmaidientu.repository;

import com.example.thuongmaidientu.exception.ProductException;
import com.example.thuongmaidientu.model.Rating;
import com.example.thuongmaidientu.model.User;
import com.example.thuongmaidientu.request.RatingRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    @Query("SELECT r From Rating r WHERE r.product.id =:productId")
    public List<Rating> getAllProductsRating(@Param("productId")Long productId);
}
