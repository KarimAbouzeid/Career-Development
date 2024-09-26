package com.example.demo.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsersDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
}
