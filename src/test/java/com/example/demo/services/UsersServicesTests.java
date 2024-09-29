package com.example.demo.services;

import com.example.demo.dtos.UsersDTO;
import com.example.demo.entities.Titles;
import com.example.demo.entities.Users;
import com.example.demo.exceptions.UserAlreadyExistsException;
import com.example.demo.mappers.UsersMapper;
import com.example.demo.repositories.TitlesRepository;
import com.example.demo.repositories.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsersServicesTests {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private TitlesRepository titlesRepository;

    @Mock
    private UsersMapper usersMapper;

    @InjectMocks
    private UsersServices usersServices;

    private UsersDTO usersDTO;
    private Users user;
    private Titles title;

    @BeforeEach
    public void setUp() {
        usersDTO = new UsersDTO();
        usersDTO.setEmail("test@test.com");
        usersDTO.setPassword("password");
        usersDTO.setManagerId(UUID.randomUUID());
        usersDTO.setTitleId(UUID.randomUUID());

        user = new Users();
        user.setId(UUID.randomUUID());
        user.setEmail("test@test.com");
        user.setPassword("password");

        title = new Titles();
        title.setId(UUID.randomUUID());
        title.setTitle("Software Engineer");
    }

    @Test
    public void testGetUser_Success() {
        UUID userId = UUID.randomUUID();
        when(usersRepository.findById(userId)).thenReturn(Optional.of(user));
        when(usersMapper.toUsersDTO(any(Users.class))).thenReturn(usersDTO);

        UsersDTO returnedUserDTO = usersServices.getUser(userId);

        assertNotNull(returnedUserDTO);
        assertEquals(usersDTO.getEmail(), returnedUserDTO.getEmail());
        verify(usersRepository, times(1)).findById(userId);
    }

    @Test
    public void testGetUser_UserNotFound_ThrowsException() {
        UUID userId = UUID.randomUUID();
        when(usersRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> usersServices.getUser(userId));
    }

    @Test
    public void testAddUser_Success() {
        when(usersRepository.existsByEmail(usersDTO.getEmail())).thenReturn(false);
        when(usersMapper.toUsers(any(UsersDTO.class))).thenReturn(user);
        when(usersRepository.findById(any(UUID.class))).thenReturn(Optional.of(user));
        when(titlesRepository.findById(any(UUID.class))).thenReturn(Optional.of(title));
        when(usersMapper.toUsersDTO(any(Users.class))).thenReturn(usersDTO);

        UsersDTO addedUser = usersServices.addUser(usersDTO);

        assertNotNull(addedUser);
        assertEquals(usersDTO.getEmail(), addedUser.getEmail());
        verify(usersRepository, times(1)).save(any(Users.class));
    }

    @Test
    public void testAddUser_UserAlreadyExists_ThrowsException() {
        when(usersRepository.existsByEmail(usersDTO.getEmail())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> usersServices.addUser(usersDTO));
    }

    @Test
    public void testUpdateUser_Success() {
        UUID userId = UUID.randomUUID();
        when(usersRepository.findById(any(UUID.class))).thenReturn(Optional.of(user));
        when(titlesRepository.findById(any(UUID.class))).thenReturn(Optional.of(title));
        when(usersMapper.toUsersDTO(any(Users.class))).thenReturn(usersDTO);

        UsersDTO updatedUser = usersServices.updateUsers(userId, usersDTO);

        assertNotNull(updatedUser);
        assertEquals(usersDTO.getEmail(), updatedUser.getEmail());
        verify(usersRepository, times(1)).save(any(Users.class));
    }

    @Test
    public void testUpdateUser_UserNotFound_ThrowsException() {
        UUID userId = UUID.randomUUID();
        when(usersRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> usersServices.updateUsers(userId, usersDTO));
    }

    @Test
    public void testLogin_Success() {
        when(usersRepository.findByEmail(usersDTO.getEmail())).thenReturn(Optional.of(user));
        when(usersMapper.toUsersDTO(any(Users.class))).thenReturn(usersDTO);

        UsersDTO loggedInUser = usersServices.login(usersDTO.getEmail(), usersDTO.getPassword());

        assertNotNull(loggedInUser);
        assertEquals(usersDTO.getEmail(), loggedInUser.getEmail());
    }

    @Test
    public void testLogin_UserNotFound_ThrowsException() {
        when(usersRepository.findByEmail(usersDTO.getEmail())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> usersServices.login(usersDTO.getEmail(), usersDTO.getPassword()));
    }

    @Test
    public void testLogin_InvalidPassword_ThrowsException() {
        when(usersRepository.findByEmail(usersDTO.getEmail())).thenReturn(Optional.of(user));

        assertThrows(IllegalArgumentException.class, () -> usersServices.login(usersDTO.getEmail(), "wrongPassword"));
    }

    @Test
    public void testDeleteUser_Success() {
        UUID userId = UUID.randomUUID();
        when(usersRepository.existsById(userId)).thenReturn(true);

        usersServices.deleteUser(userId);

        verify(usersRepository, times(1)).deleteById(userId);
    }

    @Test
    public void testDeleteUser_UserNotFound_ThrowsException() {
        UUID userId = UUID.randomUUID();
        when(usersRepository.existsById(userId)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> usersServices.deleteUser(userId));
    }

    @Test
    public void testGetAllUsers_Success() {
        PageRequest pageable = PageRequest.of(0, 10);
        List<Users> usersList = Collections.singletonList(user);
        Page<Users> usersPage = new PageImpl<>(usersList, pageable, 1);

        when(usersRepository.findAll(pageable)).thenReturn(usersPage);
        when(usersMapper.toUsersDTO(any(Users.class))).thenReturn(usersDTO);

        Page<UsersDTO> resultPage = usersServices.getAllUsers(pageable);

        assertNotNull(resultPage);
        assertEquals(1, resultPage.getTotalElements());
        verify(usersRepository, times(1)).findAll(pageable);
    }

    @Test
    public void testGetAllUsers_NoUsersFound_ThrowsException() {
        PageRequest pageable = PageRequest.of(0, 10);
        Page<Users> emptyPage = Page.empty();

        when(usersRepository.findAll(pageable)).thenReturn(emptyPage);

        assertThrows(EntityNotFoundException.class, () -> usersServices.getAllUsers(pageable));
    }
}
