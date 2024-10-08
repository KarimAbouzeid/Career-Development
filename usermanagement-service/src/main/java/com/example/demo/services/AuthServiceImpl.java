package com.example.demo.services;

import com.example.demo.components.CustomUserDetailsService;
import com.example.demo.components.JwtTokenProvider;
import com.example.demo.dtos.AuthResponseDto;
import com.example.demo.dtos.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService{

    @Autowired
    private AuthenticationManager authenicationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailsService userDetailsService;


    @Override
    public AuthResponseDto login(LoginDto loginDto) {
        try
        {
            System.out.println(loginDto.toString());

            // 01 - AuthenticationManager is used to authenticate the user
            Authentication authentication = authenicationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                    loginDto.getEmail(),
                    loginDto.getPassword()
            ));

        /* 02 - SecurityContextHolder is used to allows the rest of the application to know
        that the user is authenticated and can use user data from Authentication object */
            SecurityContextHolder.getContext().setAuthentication(authentication);

            AuthResponseDto authResponseDto = new AuthResponseDto();


            // 03 - Generate the token based on username and secret key
            String token = jwtTokenProvider.generateToken(authentication);

            authResponseDto.setAccessToken(token);
            authResponseDto.setIsAdmin(isAdmin(authentication));

            // 04 - Return the token to controller
            return authResponseDto;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public boolean isTokenValid(String token){
         return jwtTokenProvider.validateToken(token);
    }


    @Override
    public boolean isAdmin(Authentication authentication) {
        if (authentication != null) {
            return authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        }
        return false;
    }


}
