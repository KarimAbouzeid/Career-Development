package com.example.demo.dtos;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsersDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;

    private UUID managerId;
    private UUID titleId;
}
