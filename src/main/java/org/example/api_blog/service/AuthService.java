package org.example.api_blog.service;

import org.example.api_blog.model.dto.request.LoginRequest;
import org.example.api_blog.model.dto.request.RegisterRequest;
import org.example.api_blog.model.dto.response.LoginResponse;
import org.example.api_blog.model.entity.Auth;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {
    UserDetails loadUserByUsername(String email);

    Auth register(RegisterRequest registerRequest);

    LoginResponse login(LoginRequest loginRequest);
}
