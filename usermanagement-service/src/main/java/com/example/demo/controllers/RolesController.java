package com.example.demo.controllers;

import com.example.demo.dtos.RoleDto;
import com.example.demo.services.RolesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/roles")
public class RolesController {

    @Autowired
    private RolesServices rolesServices;



    @PostMapping("/addRole")
    public ResponseEntity<RoleDto> addRole(RoleDto roleDto){
        return ResponseEntity.ok(this.rolesServices.addRole(roleDto));
    }
}
