<<<<<<<< HEAD:learnings-service/src/main/java/com/example/demo/entities/ScoreboardLevels.java
package com.example.demo.entities;
|||||||| 3f1c1b9:src/main/java/com/example/demo/entities/learningsDB/ScoreboardLevels.java
package com.example.demo.entities.learningsDB;
========
package com.example.demo.entities.LearningsDB;
>>>>>>>> 7c2b0816f1f225a9e4e08facd5aa044b469ea4b8:src/main/java/com/example/demo/entities/LearningsDB/ScoreboardLevels.java


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="scoreboardLevels")
public class ScoreboardLevels {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String levelName;

    private int minScore;

    public ScoreboardLevels(String levelName, int minScore){
        this.levelName = levelName;
        this.minScore = minScore;

    }

}
