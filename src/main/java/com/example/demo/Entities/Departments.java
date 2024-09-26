package com.example.demo.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name="Departments")
public class Departments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    public Departments() {
    }

    public Departments(UUID id,String name) {
        this.id=id;
        this.name = name;
    }


}
