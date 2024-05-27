package com.example.thuongmaidientu.service;

import com.example.thuongmaidientu.exception.ProductException;
import com.example.thuongmaidientu.model.Product;
import com.example.thuongmaidientu.model.Rating;
import com.example.thuongmaidientu.model.User;
import com.example.thuongmaidientu.repository.ProductRepository;
import com.example.thuongmaidientu.repository.RatingRepository;
import com.example.thuongmaidientu.request.RatingRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RatingServiceImplementation implements RatingService{
    private RatingRepository ratingRepository;
    private ProductService productService;

    public RatingServiceImplementation(RatingRepository ratingRepository, ProductService productService) {
        this.ratingRepository = ratingRepository;
        this.productService = productService;
    }

    @Override
    public Rating createRating(RatingRequest req, User user) throws ProductException {
        Product product = productService.findProductById(req.getProductId());

        product.setNumRatings(product.getNumRatings() + 1);
        Rating rating = new Rating();
        rating.setProduct(product);
        rating.setUser(user);
        rating.setRating(req.getRating());
        rating.setLocalDateTime(LocalDateTime.now());
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getProductsRating(Long productId) {

        return ratingRepository.getAllProductsRating(productId);
    }
}
