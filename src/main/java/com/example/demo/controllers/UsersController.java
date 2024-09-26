package com.example.demo.controllers;

import com.example.demo.dtos.UsersDTO;
import com.example.demo.services.UsersServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UsersServices usersServices;

    @Autowired
    public UsersController(UsersServices usersServices) {
        this.usersServices = usersServices;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsersDTO> getUser(@PathVariable UUID id) {
        UsersDTO userDTO = usersServices.getUser(id);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping
    public ResponseEntity<UsersDTO> addUser(@RequestBody UsersDTO usersDTO) {
        UsersDTO createdUser = usersServices.addUser(usersDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsersDTO> updateUser(@PathVariable UUID id, @RequestBody UsersDTO usersUpdateDTO) {
        UsersDTO updatedUser = usersServices.updateUsers(id, usersUpdateDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {
        usersServices.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
