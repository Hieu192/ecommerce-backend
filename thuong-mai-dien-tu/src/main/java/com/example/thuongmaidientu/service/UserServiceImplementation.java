package com.example.thuongmaidientu.service;

import com.example.thuongmaidientu.config.JwtProvider;
import com.example.thuongmaidientu.exception.UserException;
import com.example.thuongmaidientu.model.User;
import com.example.thuongmaidientu.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService{
    private UserRepository userRepository;
    private JwtProvider jwtProvider;

    public UserServiceImplementation(UserRepository userRepository, JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
    }
    @Override
    public User findUserById(Long userId) throws UserException {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            return user.get();
        }

        throw new UserException("user không timf thấy bởi id" + userId);
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        String email = jwtProvider.getEmailFromToken(jwt);
        User user = userRepository.findByEmail(email);

        if(user==null) {
            throw new UserException("user không tìm thấy bởi email là : " + email);
        }
        return user;
    }
}
