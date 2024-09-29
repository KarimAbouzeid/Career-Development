package com.example.demo.entities.UsersDB;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Departments")
public class Departments {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    @OneToMany(mappedBy = "departmentId")
    private Set<Titles> titles;

    public Departments(String name) {
        this.name = name;
    }


}
