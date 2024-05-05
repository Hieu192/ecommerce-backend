package com.example.thuongmaidientu.controller;

import com.example.thuongmaidientu.exception.ProductException;
import com.example.thuongmaidientu.exception.UserException;
import com.example.thuongmaidientu.model.Rating;
import com.example.thuongmaidientu.model.Review;
import com.example.thuongmaidientu.model.User;
import com.example.thuongmaidientu.request.RatingRequest;
import com.example.thuongmaidientu.request.ReviewRequest;
import com.example.thuongmaidientu.service.RatingService;
import com.example.thuongmaidientu.service.ReviewService;
import com.example.thuongmaidientu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    private UserService userService;

    @Autowired
    private ReviewService reviewService;

    @PostMapping("/create")
    public ResponseEntity<Review> createReview(@RequestBody ReviewRequest req, @RequestHeader("Authorization")String jwt) throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);

        Review review = reviewService.createReview(req, user);

        return new ResponseEntity<Review>(review, HttpStatus.CREATED);
    }

    @GetMapping("product/{productId}")
    public ResponseEntity<List<Review>> getProductsReview(@PathVariable Long productId, @RequestHeader("Authorization")String jwt) throws UserException, ProductException{
        User user = userService.findUserProfileByJwt(jwt);

        List<Review> reviews = reviewService.getAllReview(productId);
        return new ResponseEntity<>(reviews, HttpStatus.ACCEPTED);
    }
}
