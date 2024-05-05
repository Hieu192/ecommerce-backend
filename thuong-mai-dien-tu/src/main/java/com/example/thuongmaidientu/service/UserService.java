package com.example.thuongmaidientu.service;

import com.example.thuongmaidientu.exception.UserException;
import com.example.thuongmaidientu.model.User;

public interface UserService {
    public User findUserById(Long userId) throws UserException;

    public  User findUserProfileByJwt(String jwt) throws UserException;

}
