package org.example.api_blog.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.api_blog.jwt.JwtAuthService;
import org.example.api_blog.model.dto.request.LoginRequest;
import org.example.api_blog.model.dto.request.RegisterRequest;
import org.example.api_blog.model.dto.response.LoginResponse;
import org.example.api_blog.model.entity.Auth;
import org.example.api_blog.repository.AuthRepo;
import org.example.api_blog.service.AuthService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthRepo authRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthService jwtAuthService;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Auth auth = authRepo.findByEmail(email);
        if(auth == null){
            throw new UsernameNotFoundException("User not found with email: " + email );
        }
        return auth;
    }

    @Override
    public Auth register(RegisterRequest registerRequest) {
        Auth auth = new Auth();
        auth.setUserName(registerRequest.getUserName());
        auth.setEmail(registerRequest.getEmail());
        auth.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        auth.setTokenVersion(0);
        auth.setCreatedAt(registerRequest.getCreatedAt());
        return authRepo.register(auth);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Auth auth = authRepo.findByEmail(loginRequest.getEmail());
        if(auth == null){
            throw new UsernameNotFoundException("User not found with email: " + loginRequest.getEmail());
        }
        if(!passwordEncoder.matches(loginRequest.getPassword(), auth.getPassword())){
            throw new UsernameNotFoundException("Password is incorrect");
        }

        String token = jwtAuthService.generateToken(auth);
        return LoginResponse.builder().token(token).build();
    }

    @Override
    public Integer logoutAll(String email) {
        Auth auth = authRepo.findByEmail(email);

        if (auth == null) {
            throw new RuntimeException("User not found");
        }
        return authRepo.incrementTokenVersion(auth.getUserId());
    }

}
