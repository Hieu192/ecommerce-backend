package com.example.thuongmaidientu.controller;

import com.example.thuongmaidientu.exception.ProductException;
import com.example.thuongmaidientu.exception.UserException;
import com.example.thuongmaidientu.model.Rating;
import com.example.thuongmaidientu.model.User;
import com.example.thuongmaidientu.request.RatingRequest;
import com.example.thuongmaidientu.service.RatingService;
import com.example.thuongmaidientu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {
    @Autowired
    private UserService userService;

    @Autowired
    private RatingService ratingService;

    @PostMapping("/create")
    public ResponseEntity<Rating> createRating(@RequestBody RatingRequest req, @RequestHeader("Authorization")String jwt) throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);

        Rating rating = ratingService.createRating(req, user);

        return new ResponseEntity<Rating>(rating, HttpStatus.CREATED);
    }

    @GetMapping("product/{productId}")
    public ResponseEntity<List<Rating>> getProductsRating(@PathVariable Long productId, @RequestHeader("Authorization")String jwt) throws UserException, ProductException{
        User user = userService.findUserProfileByJwt(jwt);

        List<Rating> ratings = ratingService.getProductsRating(productId);
        return new ResponseEntity<>(ratings, HttpStatus.CREATED);
    }
}
