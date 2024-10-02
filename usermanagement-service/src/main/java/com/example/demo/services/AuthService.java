package com.example.demo.services;

import com.example.demo.dtos.LoginDto;

public interface AuthService {

    String login(LoginDto loginDto);
}
