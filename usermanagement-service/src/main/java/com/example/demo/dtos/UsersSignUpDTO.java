package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsersSignUpDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;

}
