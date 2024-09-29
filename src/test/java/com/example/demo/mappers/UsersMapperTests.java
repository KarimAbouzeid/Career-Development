package com.example.demo.mappers;

import com.example.demo.dtos.UsersDTO;
import com.example.demo.entities.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class UsersMapperTests {

    @Autowired
    private UsersMapper usersMapper;

    private Users users;
    private UsersDTO usersDTO;

    @BeforeEach
    public void setUp() {
        users = new Users();
        users.setId(UUID.randomUUID());
        users.setFirstName("John");
        users.setLastName("Doe");
        users.setEmail("johndoe@example.com");
        users.setPassword("password123");

        usersDTO = new UsersDTO();
        usersDTO.setFirstName("Jane");
        usersDTO.setLastName("Doe");
        usersDTO.setEmail("janedoe@example.com");
        usersDTO.setPassword("password456");
    }

    @Test
    public void toUsersDTO_ShouldMapUsersToUsersDTO() {
        UsersDTO result = usersMapper.toUsersDTO(users);

        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo(users.getFirstName());
        assertThat(result.getLastName()).isEqualTo(users.getLastName());
        assertThat(result.getEmail()).isEqualTo(users.getEmail());
        assertThat(result.getPassword()).isEqualTo(users.getPassword());
    }

    @Test
    public void toUsers_ShouldMapUsersDTOToUsers() {

        Users result = usersMapper.toUsers(usersDTO);

        assertThat(result).isNotNull();
        assertThat(result.getFirstName()).isEqualTo(usersDTO.getFirstName());
        assertThat(result.getLastName()).isEqualTo(usersDTO.getLastName());
        assertThat(result.getEmail()).isEqualTo(usersDTO.getEmail());
        assertThat(result.getPassword()).isEqualTo(usersDTO.getPassword());
    }

    @Test
    public void updateUsersFromDto_ShouldUpdateUsersWithUsersDTO() {
        users.setFirstName("OldName");

        usersMapper.updateUsersFromDto(usersDTO, users);

        assertThat(users).isNotNull();
        assertThat(users.getFirstName()).isEqualTo(usersDTO.getFirstName());
        assertThat(users.getLastName()).isEqualTo(usersDTO.getLastName());
        assertThat(users.getEmail()).isEqualTo(usersDTO.getEmail());
        assertThat(users.getPassword()).isEqualTo(usersDTO.getPassword());
    }
}
