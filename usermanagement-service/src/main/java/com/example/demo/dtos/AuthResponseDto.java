package com.example.demo.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class AuthResponseDto {

    private String accessToken;
    private boolean isAdmin;
    private boolean isManager;
    private UUID userId;


    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public void setIsManager(boolean isManager) {
        this.isManager = isManager;
    }

}
