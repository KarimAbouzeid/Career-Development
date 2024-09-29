package com.example.demo.controllers.usersDB;

import com.example.demo.dtos.usersDB.UsersDTO;
import com.example.demo.services.UsersDB.UsersServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("/allUsersPaginated")
    public ResponseEntity<Page<UsersDTO>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UsersDTO> usersDTOS = usersServices.getAllUsers(pageable);
        return ResponseEntity.ok(usersDTOS);
    }

    @PostMapping("/login")
    public ResponseEntity<UsersDTO> login(@RequestParam String email, @RequestParam String password) {
            UsersDTO userDTO = usersServices.login(email, password);
            return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/signUp")
    public ResponseEntity<UsersDTO> signUp(@RequestBody UsersDTO usersDTO) {
        UsersDTO createdUser = usersServices.addUser(usersDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
