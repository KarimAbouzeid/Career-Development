package com.example.demo.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class AuthResponseDto {

    private String accessToken;
    private boolean isAdmin;
    private UUID userId;


    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
