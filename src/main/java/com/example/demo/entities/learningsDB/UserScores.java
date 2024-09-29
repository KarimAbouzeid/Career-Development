package com.example.demo.entities.learningsDB;


import com.example.demo.entities.UsersDB.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="userScores")
public class UserScores {
    @Id
    private UUID userId;


    private int score;
}
