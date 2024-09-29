package com.example.demo.dtos.usersDB;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TitlesDTO {

    private String title;
    private Boolean isManager;

    private UUID departmentId;


}
