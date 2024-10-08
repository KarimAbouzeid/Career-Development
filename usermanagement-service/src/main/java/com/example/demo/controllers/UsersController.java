package com.example.demo.controllers;

import com.example.demo.dtos.UsersDTO;
import com.example.demo.dtos.UsersSignUpDTO;
import com.example.demo.services.UsersServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*", allowCredentials = "true")
@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UsersServices usersServices;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<String> helloAdmin(){
        return ResponseEntity.ok("Hello Admin");
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public ResponseEntity<String> helloUser(){
        return ResponseEntity.ok("Hello User");
    }

    @Autowired
    public UsersController(UsersServices usersServices) {
        this.usersServices = usersServices;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsersDTO> getUser(@PathVariable UUID id) {
        UsersDTO userDTO = usersServices.getUser(id);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<UsersDTO> updateUser( @RequestBody UsersDTO usersUpdateDTO) {
        UsersDTO updatedUser = usersServices.updateUsers(usersUpdateDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {
        usersServices.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/allUsersPaginated")
    public ResponseEntity<Page<UsersDTO>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<UsersDTO> usersDTOS = usersServices.getAllUsers(pageable);
        return ResponseEntity.ok(usersDTOS);
    }

    @PostMapping("/login")
    public ResponseEntity<UsersDTO> login( @RequestParam String email, @RequestParam String password) {
            UsersDTO userDTO = usersServices.login(email, password);
            return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/signUp")
    public ResponseEntity<UsersSignUpDTO> signUp(@RequestBody UsersSignUpDTO usersDTO) {
        System.out.println("HEREEEE");
        UsersSignUpDTO createdUser = usersServices.signUp(usersDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PostMapping("/add")
    public ResponseEntity<UsersDTO> addUser(@RequestBody UsersDTO usersDTO) {
        UsersDTO createdUser = usersServices.addUser(usersDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/freeze")
    public ResponseEntity<String> freezeUser(@RequestParam String email) {
        usersServices.freezeUserByEmail(email);
        return ResponseEntity.ok("User with email " + email + " has been frozen.");
    }

    @PutMapping("/unfreeze")
    public ResponseEntity<String> unfreezeUser(@RequestParam String email) {
        usersServices.unfreezeUserByEmail(email);
        return ResponseEntity.ok("User with email " + email + " has been unfrozen.");
    }


    @DeleteMapping("/deleteByEmail")
    public ResponseEntity<String> deleteUserByEmail(@RequestParam String email) {
        usersServices.deleteUserByEmail(email);
        return ResponseEntity.ok("User with email " + email + " has been deleted.");
    }

    @PutMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestParam String email, @RequestParam String newPassword) {
        usersServices.resetPassword(email, newPassword);
        return ResponseEntity.ok("Password for user with email " + email + " has been reset.");
    }

    @PutMapping("/assignManager")
    public ResponseEntity<String> assignManagerByEmail(@RequestParam String userEmail, @RequestParam String managerEmail) {
        usersServices.assignManager(userEmail, managerEmail);
        return ResponseEntity.ok("Manager assigned successfully.");
    }

    @PutMapping("/assignTitle")
    public ResponseEntity<String> assignTitleByEmail(@RequestParam String email, @RequestParam UUID titleId) {
        usersServices.assignTitleByEmail(email, titleId);
        return ResponseEntity.ok("Title assigned successfully");
    }



}
