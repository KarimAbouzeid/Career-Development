package com.example.demo.services;

import com.example.demo.dtos.AuthResponseDto;
import com.example.demo.dtos.LoginDto;
import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface AuthService {

    AuthResponseDto login(LoginDto loginDto);

    boolean isTokenValid(String token);

    boolean isAdmin(Authentication authentication);

    UUID getUserId(String email);
}
