package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoostersDTO {
    private UUID id;
    private String name;
    private String type;
    private int value;
    private boolean isActive;
}
