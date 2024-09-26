package com.example.demo.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name="Titles")
public class Titles {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String title;

    private Boolean isManager;

    public Titles() {
    }

    public Titles(UUID id,String title, Boolean isManager) {
        this.id = id;
        this.title = title;
        this.isManager = isManager;
    }


}
