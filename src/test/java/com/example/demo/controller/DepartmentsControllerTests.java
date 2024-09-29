package com.example.demo.controller;


import com.example.demo.controllers.DepartmentsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(DepartmentsController.class)
public class DepartmentsControllerTests {

    @Autowired
    private MockMvc mockMvc;
}
