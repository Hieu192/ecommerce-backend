package com.example.thuongmaidientu.service;

import com.example.thuongmaidientu.exception.ProductException;
import com.example.thuongmaidientu.model.Rating;
import com.example.thuongmaidientu.model.Review;
import com.example.thuongmaidientu.model.User;
import com.example.thuongmaidientu.request.RatingRequest;
import com.example.thuongmaidientu.request.ReviewRequest;

import java.util.List;

public interface ReviewService {
    public Review createReview(ReviewRequest req, User user) throws ProductException;
    public List<Review> getAllReview(Long productId);
}
