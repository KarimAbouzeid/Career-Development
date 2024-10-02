package com.example.demo.controller;

import com.example.demo.controllers.AuthController;
import com.example.demo.controllers.UsersController;
import com.example.demo.dtos.AuthResponseDto;
import com.example.demo.dtos.LoginDto;
import com.example.demo.services.AuthService;
import exceptions.GlobalExceptionHandler;
import exceptions.InvalidCredentialsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;

    private LoginDto loginDto;

    @BeforeEach
    public void setUp() {
        loginDto = new LoginDto();
        loginDto.setEmail("user@example.com");
        loginDto.setPassword("password");
    }

    @Test
    public void login_ReturnsAuthResponseDto() {
        String mockToken = "mockToken";
        when(authService.login(any(LoginDto.class))).thenReturn(mockToken);

        ResponseEntity<AuthResponseDto> response = authController.login(loginDto);

        AuthResponseDto responseBody = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assert responseBody != null;
        assertEquals(mockToken, Objects.requireNonNull(response.getHeaders().getFirst(HttpHeaders.SET_COOKIE)).split(";")[0].split("=")[1]);
        assertEquals(mockToken, responseBody.getAccessToken());}

}
