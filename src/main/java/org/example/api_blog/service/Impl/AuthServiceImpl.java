package org.example.api_blog.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.api_blog.model.dto.request.RegisterRequest;
import org.example.api_blog.model.entity.Auth;
import org.example.api_blog.repository.AuthRepo;
import org.example.api_blog.service.AuthService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthRepo authRepo;

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
        return authRepo.register(registerRequest);
    }
}
