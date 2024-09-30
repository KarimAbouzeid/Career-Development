package com.example.demo.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;

    @ManyToOne
    @JoinColumn(name = "manager_id", nullable = true)
    private Users manager;


    @ManyToOne
    @JoinColumn(name="title_id", nullable = true)
    private Titles titleId;
}

