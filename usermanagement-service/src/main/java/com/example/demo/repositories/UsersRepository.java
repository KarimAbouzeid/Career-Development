package com.example.demo.repositories;

import com.example.demo.entities.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface UsersRepository extends JpaRepository<Users, UUID> {
    boolean existsByEmail(String email);

    Optional<Users> findByEmail(String email);

    Page<Users> findAll(Pageable pageable);

    @Query("SELECT u FROM Users u WHERE u.manager.id = :managerId")
    List<Users> findByManagerId(@Param("managerId") UUID managerId);
}
