package com.example.demo.dtos;

import com.example.demo.enums.Title;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CareerPackageRequestDto {

    private Title title;

    private String googleDocLink;



}
