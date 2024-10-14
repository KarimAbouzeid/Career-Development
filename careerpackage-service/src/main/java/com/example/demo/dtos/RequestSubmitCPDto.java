package com.example.demo.dtos;

import com.example.demo.enums.Title;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestSubmitCPDto {

    private Title title;
    private UUID userId;
    private String googleDocLink;

}
