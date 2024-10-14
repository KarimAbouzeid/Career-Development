package com.example.demo.services;

import com.example.demo.components.JwtTokenProvider;
import com.example.demo.dtos.LoginDto;
import exceptions.InvalidCredentialsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Mock
    private Authentication authentication;

    private LoginDto loginDto;

    @BeforeEach
    public void setUp() {
        loginDto = new LoginDto();
        loginDto.setEmail("user@example.com");
        loginDto.setPassword("password");
    }

//    @Test
//    public void testLogin_validCredentials_returnsToken() {
//        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
//                .thenReturn(authentication);
//        when(jwtTokenProvider.generateToken(authentication)).thenReturn("mockToken");
//
//        String token = authService.login(loginDto);
//
//        assertEquals("mockToken", token);
//        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
//        verify(jwtTokenProvider).generateToken(authentication);
//
//        // check that the authentication was set in SecurityContextHolder
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        assertEquals(authentication, auth);
//    }

    @Test
    public void testLogin_invalidCredentials_throwsException() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new InvalidCredentialsException("Invalid credentials"));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            authService.login(loginDto);
        });
        assertEquals("Invalid credentials", thrown.getMessage());
    }
}
