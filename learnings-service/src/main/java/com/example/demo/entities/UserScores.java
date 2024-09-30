<<<<<<<< HEAD:learnings-service/src/main/java/com/example/demo/entities/UserScores.java
package com.example.demo.entities;
|||||||| 3f1c1b9:src/main/java/com/example/demo/entities/learningsDB/UserScores.java
package com.example.demo.entities.learningsDB;
========
package com.example.demo.entities.LearningsDB;
>>>>>>>> 7c2b0816f1f225a9e4e08facd5aa044b469ea4b8:src/main/java/com/example/demo/entities/LearningsDB/UserScores.java

<<<<<<<< HEAD:learnings-service/src/main/java/com/example/demo/entities/UserScores.java

|||||||| 3f1c1b9:src/main/java/com/example/demo/entities/learningsDB/UserScores.java

import com.example.demo.entities.UsersDB.Users;
========
>>>>>>>> 7c2b0816f1f225a9e4e08facd5aa044b469ea4b8:src/main/java/com/example/demo/entities/LearningsDB/UserScores.java
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
