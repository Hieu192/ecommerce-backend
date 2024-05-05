package com.example.thuongmaidientu.controller;

import com.example.thuongmaidientu.exception.UserException;
import com.example.thuongmaidientu.model.User;
import com.example.thuongmaidientu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("profile")
    public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization")String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        return new ResponseEntity<User>(user, HttpStatus.ACCEPTED);
    }
}
