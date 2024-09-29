package com.example.demo.entities.UsersDB;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Titles")
public class Titles {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String title;

    private Boolean isManager;

    @OneToMany(mappedBy = "titleId")
    private Set<Users> users;

    @ManyToOne
    @JoinColumn(name="department_id", nullable = true)
    private Departments departmentId;


}
