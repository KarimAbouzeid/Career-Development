package com.example.demo.dtos;

import lombok.Data;

@Data
public class AuthResponseDto {

    private String accessToken;
    private boolean isAdmin;

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
