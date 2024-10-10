package com.example.demo.repositories;

import com.example.demo.entities.UserLearnings;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.UUID;

@Repository
public interface UserLearningsRepository extends JpaRepository<UserLearnings, UUID> {


    List<UserLearnings> findByUserId(UUID userId);

    void deleteByLearning_Id(@Param("learning_id") UUID learningId); // Note the underscore here


}
