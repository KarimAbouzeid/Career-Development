package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestSubmitCPwithID {

    private UUID careerPackageId;
    private UUID userId;
    private String googleDocLink;

}
