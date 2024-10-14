package com.example.demo.services;

import com.example.demo.dtos.TitlesDTO;
import com.example.demo.dtos.UsersSignUpDTO;
import com.example.demo.entities.Role;
import com.example.demo.mappers.TitlesMapper;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UsersRepository;
import com.example.demo.repositories.TitlesRepository;
import com.example.demo.mappers.UsersMapper;
import com.example.demo.dtos.UsersDTO;
import com.example.demo.entities.Users;
import com.example.demo.entities.Titles;

import com.example.demo.exceptions.UserAlreadyExistsException;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsersServicesTests {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private TitlesRepository titlesRepository;

    @Mock
    private TitlesServices titlesServices;

    @Mock
    private TitlesMapper titlesMapper;

    @Mock
    private UsersMapper usersMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsersServices usersServices;

    private UsersDTO usersDTO;
    private UsersSignUpDTO usersSignUpDTO;
    private Users user;
    private Titles title;
    private TitlesDTO titleDTO;


    @BeforeEach
    public void setUp() {
        usersSignUpDTO = new UsersSignUpDTO();
        usersSignUpDTO.setEmail("test@test.com");
        usersSignUpDTO.setPassword("password");

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

        titleDTO = new TitlesDTO();

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
        UUID managerId = UUID.randomUUID();
        UUID titleId = UUID.randomUUID();

        usersDTO.setId(userId);
        usersDTO.setManagerId(managerId);
        usersDTO.setTitleId(titleId);

        when(usersRepository.findById(eq(userId))).thenReturn(Optional.of(user));
        when(usersRepository.findById(eq(managerId))).thenReturn(Optional.of(user));
        when(titlesRepository.findById(eq(titleId))).thenReturn(Optional.of(title));
        when(usersMapper.toUsersDTO(any(Users.class))).thenReturn(usersDTO);

        UsersDTO updatedUser = usersServices.updateUsers(usersDTO);

        assertNotNull(updatedUser);
        assertEquals(usersDTO.getEmail(), updatedUser.getEmail());
        verify(usersRepository, times(1)).save(any(Users.class));
    }

    @Test
    public void testUpdateUser_UserNotFound_ThrowsException() {
        when(usersRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> usersServices.updateUsers(usersDTO));
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
    public void testSignUp_Success() {
        when(usersRepository.existsByEmail(usersSignUpDTO.getEmail())).thenReturn(false);
        when(roleRepository.findByName("USER")).thenReturn(Optional.of(new Role()));
        when(usersMapper.toUsers(any(UsersSignUpDTO.class))).thenReturn(user);
        when(usersMapper.toUsersSignupDTO(any(Users.class))).thenReturn(usersSignUpDTO);
        when(usersRepository.save(any(Users.class))).thenReturn(user);

        // Act
        UsersSignUpDTO addedUser = usersServices.signUp(usersSignUpDTO);

        // Assert
        assertNotNull(addedUser);
        assertEquals(usersSignUpDTO.getEmail(), addedUser.getEmail());
        verify(usersRepository, times(1)).save(any(Users.class)); // Ensure the user was saved
        verify(roleRepository, times(1)).findByName("USER"); // Ensure the role was fetched
    }

    @Test
    public void testSignUp_UserAlreadyExists_ThrowsException() {
        when(usersRepository.existsByEmail(usersSignUpDTO.getEmail())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> usersServices.signUp(usersSignUpDTO));
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


    @Test
    public void testFreezeUserByEmail_Success() {
        String email = "test@example.com";
        when(usersRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(usersMapper.toUsersDTO(any(Users.class))).thenReturn(usersDTO);

        UsersDTO returnedUserDTO = usersServices.freezeUserByEmail(email);

        assertNotNull(returnedUserDTO);
        assertTrue(user.isFrozen());
        assertEquals(usersDTO.getEmail(), returnedUserDTO.getEmail());
        verify(usersRepository, times(1)).findByEmail(email);
    }

    @Test
    public void testFreezeUserByEmail_UserNotFound_ThrowsException() {
        String email = "test@example.com";
        when(usersRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> usersServices.freezeUserByEmail(email));
    }

    @Test
    public void testUnfreezeUserByEmail_Success() {
        String email = "test@example.com";
        user.setFrozen(true); // User is frozen
        when(usersRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(usersMapper.toUsersDTO(any(Users.class))).thenReturn(usersDTO);

        UsersDTO returnedUserDTO = usersServices.unfreezeUserByEmail(email);

        assertNotNull(returnedUserDTO);
        assertFalse(user.isFrozen());
        assertEquals(usersDTO.getEmail(), returnedUserDTO.getEmail());
        verify(usersRepository, times(1)).findByEmail(email);
    }

    @Test
    public void testUnfreezeUserByEmail_UserNotFound_ThrowsException() {
        String email = "test@example.com";
        when(usersRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> usersServices.unfreezeUserByEmail(email));
    }

    @Test
    public void testDeleteUserByEmail_Success() {
        String email = "test@example.com";
        when(usersRepository.findByEmail(email)).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> usersServices.deleteUserByEmail(email));

        verify(usersRepository, times(1)).delete(user);
        verify(usersRepository, times(1)).findByEmail(email);
    }

    @Test
    public void testDeleteUserByEmail_UserNotFound_ThrowsException() {
        String email = "test@example.com";
        when(usersRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> usersServices.deleteUserByEmail(email));
    }

    @Test
    public void testResetPassword_Success() {
        String email = "test@example.com";
        String newPassword = "newPassword";
        when(usersRepository.findByEmail(email)).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> usersServices.resetPassword(email, newPassword));

        verify(usersRepository, times(1)).save(user);
        assertEquals(passwordEncoder.encode(newPassword), user.getPassword());
        verify(usersRepository, times(1)).findByEmail(email);
    }

    @Test
    public void testResetPassword_UserNotFound_ThrowsException() {
        String email = "test@example.com";
        when(usersRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> usersServices.resetPassword(email, "newPassword"));
    }

    @Test
    public void testAssignManager_Success() {
        String userEmail = "user@example.com";
        String managerEmail = "manager@example.com";
        Users manager = new Users();
        manager.setEmail(managerEmail);

        when(usersRepository.findByEmail(userEmail)).thenReturn(Optional.of(user));
        when(usersRepository.findByEmail(managerEmail)).thenReturn(Optional.of(manager));

        assertDoesNotThrow(() -> usersServices.assignManager(userEmail, managerEmail));

        assertEquals(manager, user.getManager());
        verify(usersRepository, times(1)).save(user);
        verify(usersRepository, times(2)).findByEmail(anyString());
    }

    @Test
    public void testAssignManager_UserNotFound_ThrowsException() {
        String userEmail = "user@example.com";
        String managerEmail = "manager@example.com";
        when(usersRepository.findByEmail(userEmail)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> usersServices.assignManager(userEmail, managerEmail));
    }

    @Test
    public void testAssignManager_ManagerNotFound_ThrowsException() {
        String userEmail = "user@example.com";
        String managerEmail = "manager@example.com";
        when(usersRepository.findByEmail(userEmail)).thenReturn(Optional.of(user));
        when(usersRepository.findByEmail(managerEmail)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> usersServices.assignManager(userEmail, managerEmail));
    }

    @Test
    public void assignTitleByEmail_Success() {
        String userEmail = "user@example.com";
        UUID titleId = UUID.randomUUID();

        when(usersRepository.findByEmail(userEmail)).thenReturn(Optional.of(user));
        when(titlesServices.getTitles(titleId)).thenReturn(titleDTO);
        when(titlesMapper.toTitle(titleDTO)).thenReturn(title);

        assertDoesNotThrow(() -> usersServices.assignTitleByEmail(userEmail, titleId));

        assertEquals(title, user.getTitleId());
        verify(usersRepository, times(1)).save(user);
        verify(usersRepository, times(1)).findByEmail(userEmail);
        verify(titlesServices, times(1)).getTitles(titleId);
        verify(titlesMapper, times(1)).toTitle(titleDTO);
    }

    @Test
    public void assignTitleByEmail_UserNotFound_ThrowsException() {
        String userEmail = "user@example.com";
        UUID titleId = UUID.randomUUID();

        when(usersRepository.findByEmail(userEmail)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> usersServices.assignTitleByEmail(userEmail, titleId));

        verify(usersRepository, times(1)).findByEmail(userEmail);
        verify(titlesServices, never()).getTitles(any());
        verify(titlesMapper, never()).toTitle(any());
        verify(usersRepository, never()).save(any());
    }

    @Test
    public void assignTitleByEmail_TitleNotFound_ThrowsException() {
        String userEmail = "user@example.com";
        UUID titleId = UUID.randomUUID();

        when(usersRepository.findByEmail(userEmail)).thenReturn(Optional.of(user));
        when(titlesServices.getTitles(titleId)).thenThrow(new EntityNotFoundException("Title not found"));

        assertThrows(EntityNotFoundException.class, () -> usersServices.assignTitleByEmail(userEmail, titleId));

        verify(usersRepository, times(1)).findByEmail(userEmail);
        verify(titlesServices, times(1)).getTitles(titleId);
        verify(titlesMapper, never()).toTitle(any());
        verify(usersRepository, never()).save(any());
    }
}
