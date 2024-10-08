package com.example.demo.dtos;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TitlesDTO {
    private UUID id;

    private String title;
    private Boolean isManager;

    private UUID departmentId;


}
