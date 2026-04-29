package org.example.api_blog.controller;


import lombok.RequiredArgsConstructor;
import org.example.api_blog.model.dto.request.LoginRequest;
import org.example.api_blog.model.dto.request.RegisterRequest;
import org.example.api_blog.model.dto.response.ApiResponse;
import org.example.api_blog.model.dto.response.LoginResponse;
import org.example.api_blog.model.entity.Auth;
import org.example.api_blog.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auths")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Auth>> register(@RequestBody RegisterRequest registerRequest){
        Auth auth = authService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>("register is successfully",auth,HttpStatus.CREATED.value(), LocalDateTime.now())
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest){
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>("login is successfully",response,HttpStatus.CREATED.value(), LocalDateTime.now())
        );
    }
}
