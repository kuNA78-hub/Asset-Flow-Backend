package com.example.demo.proj.controller;

import com.example.demo.proj.dto.JwtResponseDto;
import com.example.demo.proj.dto.LoginRequestDto;
import com.example.demo.proj.dto.UserRequestDto;
import com.example.demo.proj.dto.UserResponseDto;
import com.example.demo.proj.security.JwtTokenProvider;
import com.example.demo.proj.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDto> authenticateUser(@Valid @RequestBody LoginRequestDto loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        final UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        final String jwt = jwtTokenProvider.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponseDto(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@Valid @RequestBody UserRequestDto signUpRequest) {
        // You might want to add logic here to check if the email is already taken
        UserResponseDto createdUser = userService.createUser(signUpRequest);
        return ResponseEntity.ok(createdUser);
    }
}