package com.example.thuongmaidientu.controller;

import com.example.thuongmaidientu.config.JwtProvider;
import com.example.thuongmaidientu.exception.UserException;
import com.example.thuongmaidientu.model.Cart;
import com.example.thuongmaidientu.model.User;
import com.example.thuongmaidientu.reponse.AuthResponse;
import com.example.thuongmaidientu.repository.UserRepository;
import com.example.thuongmaidientu.request.LoginRequest;
import com.example.thuongmaidientu.service.CartService;
import com.example.thuongmaidientu.service.CustomerUserServiceImplementation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private JwtProvider jwtProvider;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private CustomerUserServiceImplementation customerUserServiceImplementation;
    private CartService cartService;

    public AuthController(UserRepository userRepository, CustomerUserServiceImplementation customerUserServiceImplementation, PasswordEncoder passwordEncoder, JwtProvider jwtProvider, CartService cartService) {
        this.userRepository= userRepository;
        this.customerUserServiceImplementation = customerUserServiceImplementation;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
        this.cartService = cartService;
    }
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandle(@RequestBody User user) throws UserException {
        String email = user.getEmail();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();

        User isEmailExist = userRepository.findByEmail(email);

        if(isEmailExist!=null) {
            throw new UserException("Email is Already Used With Another Account");
        }

        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setFirstName(firstName);
        createdUser.setLastName(lastName);

        User saveUser = userRepository.save(createdUser);
        Cart cart = cartService.createCart(saveUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(saveUser.getEmail(), saveUser.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Đăng ký thành công");
        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginUserHandle(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Đăng nhập thành công");
        return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customerUserServiceImplementation.loadUserByUsername(username);

        if(userDetails == null) {
            throw new BadCredentialsException("user không hợp lệ ");
        }

        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("Mật khẩu không hợp lệ");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}




