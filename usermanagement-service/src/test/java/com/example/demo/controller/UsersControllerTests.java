package com.example.demo.controller;


import com.example.demo.controllers.UsersController;
import com.example.demo.dtos.UsersDTO;
import com.example.demo.dtos.UsersSignUpDTO;
import exceptions.GlobalExceptionHandler;
import exceptions.InvalidCredentialsException;
import com.example.demo.services.UsersServices;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsersController.class)
@ContextConfiguration(classes = {UsersController.class, GlobalExceptionHandler.class})
@AutoConfigureMockMvc(addFilters = false)
public class UsersControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersServices usersServices;

    private UsersDTO usersDTO;
    private UsersSignUpDTO usersSignUpDTO;


    @BeforeEach
    public void setUp() {

        usersSignUpDTO = new UsersSignUpDTO();
        usersSignUpDTO.setFirstName("John");
        usersSignUpDTO.setLastName("Doe");
        usersSignUpDTO.setEmail("johndoe@example.com");
        usersSignUpDTO.setPassword("password");

        usersDTO = new UsersDTO();
        usersDTO.setFirstName("John");
        usersDTO.setLastName("Doe");
        usersDTO.setEmail("johndoe@example.com");
        usersDTO.setPassword("password");

    }

    @Test
    public void getUser_userExists_ReturnsUsersDTO() throws Exception {
        UUID userId = UUID.randomUUID();

        when(usersServices.getUser(userId)).thenReturn(usersDTO);

        mockMvc.perform(get("/api/users/{id}", userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("johndoe@example.com"));

        verify(usersServices, times(1)).getUser(userId);
    }

    @Test
    void getUser_userNotExists_returnsNotFound() throws Exception {
        UUID userId = UUID.randomUUID();
        when(usersServices.getUser(userId)).thenThrow(new EntityNotFoundException("User with id " + userId + " not found"));

        mockMvc.perform(get("/api/users/{id}", userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User with id " + userId + " not found"));
    }

    @Test
    void getUser_unexpectedError_returnsInternalServerError() throws Exception {
        UUID userId = UUID.randomUUID();
        when(usersServices.getUser(userId)).thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/users/{id}", userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An error occurred. Please try again later."));
    }

    @Test
    public void addUser_userDetailsEntered_ReturnsUsersDTO() throws Exception {
        when(usersServices.addUser(any(UsersDTO.class))).thenReturn(usersDTO);

        mockMvc.perform(post("/api/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"John\", \"lastName\": \"Doe\", \"email\": \"johndoe@example.com\", \"password\": \"password123\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("johndoe@example.com"));

        verify(usersServices, times(1)).addUser(any(UsersDTO.class));
    }

    @Test
    void addUser_unexpectedError_returnsInternalServerError() throws Exception {
        when(usersServices.addUser(any(UsersDTO.class))).thenThrow(new RuntimeException());

        mockMvc.perform(post("/api/users/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"John\", \"lastName\": \"Doe\", \"email\": \"johndoe@example.com\", \"password\": \"password123\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An error occurred. Please try again later."));
    }

    @Test
    public void updateUser_userExists_ReturnsUsersDTO() throws Exception {
        UUID userId = UUID.randomUUID();

        UsersDTO updatedUsersDTO = new UsersDTO();
        updatedUsersDTO.setFirstName("John");
        updatedUsersDTO.setLastName("Doe");
        updatedUsersDTO.setEmail("johndoe@example.com");
        updatedUsersDTO.setPassword("newpassword123");

        when(usersServices.updateUsers(any(UsersDTO.class))).thenReturn(updatedUsersDTO);

        mockMvc.perform(put("/api/users/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"John\", \"lastName\": \"Doe\", \"email\": \"johndoe@example.com\", \"password\": \"newpassword123\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("johndoe@example.com"))
                .andExpect(jsonPath("$.password").value("newpassword123")); // This should match now

        verify(usersServices, times(1)).updateUsers(any(UsersDTO.class));
    }

    @Test
    void updateUser_userNotExists_ReturnsNotFound() throws Exception {
        UUID userId = UUID.randomUUID();

        when(usersServices.updateUsers(any(UsersDTO.class))).thenThrow(new EntityNotFoundException("User with id " + userId + " not found"));

        mockMvc.perform(put("/api/users/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"John\", \"lastName\": \"Doe\", \"email\": \"johndoe@example.com\", \"password\": \"newpassword123\"}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User with id " + userId + " not found"));
    }

    @Test
    void updateUser_unexpectedError_returnsInternalServerError() throws Exception {
        UUID userId = UUID.randomUUID();

        when(usersServices.updateUsers( any(UsersDTO.class))).thenThrow(new RuntimeException());

        mockMvc.perform(put("/api/users/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"John\", \"lastName\": \"Doe\", \"email\": \"johndoe@example.com\", \"password\": \"newpassword123\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An error occurred. Please try again later."));
    }

    @Test
    public void deleteUser_userExists_ReturnsSuccessMessage() throws Exception {
        UUID userId = UUID.randomUUID();

        doNothing().when(usersServices).deleteUser(userId);

        mockMvc.perform(delete("/api/users/delete/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(content().string("User deleted successfully"));

        verify(usersServices, times(1)).deleteUser(userId);
    }

    @Test
    public void deleteUser_userNotExists_ReturnsNotFound() throws Exception {
        UUID userId = UUID.randomUUID();

        doThrow(new EntityNotFoundException("User with id " + userId + " not found"))
                .when(usersServices).deleteUser(userId);

        mockMvc.perform(delete("/api/users/delete/{id}", userId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User with id " + userId + " not found"));
    }

    @Test
    void deleteUser_unexpectedError_returnsInternalServerError() throws Exception {
        UUID userId = UUID.randomUUID();

        doThrow(new RuntimeException())
                .when(usersServices).deleteUser(userId);

        mockMvc.perform(delete("/api/users/delete/{id}", userId))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An error occurred. Please try again later."));
    }


    @Test
    public void login_validCredentials_ReturnsUsersDTO() throws Exception {
        String email = "johndoe@example.com";
        String password = "password123";

        when(usersServices.login(email, password)).thenReturn(usersDTO);

        mockMvc.perform(post("/api/users/login")
                        .param("email", email)
                        .param("password", password))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("johndoe@example.com"));

        verify(usersServices, times(1)).login(email, password);
    }

    @Test
    void login_invalidPassword_returnsUnauthorized() throws Exception {
        String email = "johndoe@example.com";
        String password = "wrongpassword";

        when(usersServices.login(email, password)).thenThrow(new InvalidCredentialsException("Invalid password"));

        mockMvc.perform(post("/api/users/login")
                        .param("email", email)
                        .param("password", password))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Invalid password"));
    }


    @Test
    public void signUp_userDetailsEntered_ReturnsUsersSignUpDTO() throws Exception {
        when(usersServices.signUp(any(UsersSignUpDTO.class))).thenReturn(usersSignUpDTO);

        mockMvc.perform(post("/api/users/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"John\", \"lastName\": \"Doe\", \"email\": \"johndoe@example.com\", \"password\": \"password123\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("johndoe@example.com"));

        verify(usersServices, times(1)).signUp(any(UsersSignUpDTO.class));
    }

    @Test
    void signUp_unexpectedError_returnsInternalServerError() throws Exception {
        when(usersServices.signUp(any(UsersSignUpDTO.class))).thenThrow(new RuntimeException());

        mockMvc.perform(post("/api/users/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\": \"John\", \"lastName\": \"Doe\", \"email\": \"johndoe@example.com\", \"password\": \"password123\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An error occurred. Please try again later."));
    }


    @Test
    public void freezeUser_userExists_ReturnsSuccessMessage() throws Exception {
        String email = "test@example.com";
        doReturn(new UsersDTO()).when(usersServices).freezeUserByEmail(email);

        mockMvc.perform(put("/api/users/freeze").param("email", email))
                .andExpect(status().isOk())
                .andExpect(content().string("User with email " + email + " has been frozen."));

        verify(usersServices, times(1)).freezeUserByEmail(email);
    }

    @Test
    public void freezeUser_userNotExists_ReturnsNotFound() throws Exception {
        String email = "test@example.com";
        doThrow(new EntityNotFoundException("User not found with email: " + email))
                .when(usersServices).freezeUserByEmail(email);

        mockMvc.perform(put("/api/users/freeze").param("email", email))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found with email: " + email));

        verify(usersServices, times(1)).freezeUserByEmail(email);
    }

    @Test
    public void unfreezeUser_userExists_ReturnsSuccessMessage() throws Exception {
        String email = "test@example.com";
        doReturn(new UsersDTO()).when(usersServices).unfreezeUserByEmail(email);

        mockMvc.perform(put("/api/users/unfreeze").param("email", email))
                .andExpect(status().isOk())
                .andExpect(content().string("User with email " + email + " has been unfrozen."));

        verify(usersServices, times(1)).unfreezeUserByEmail(email);
    }

    @Test
    public void unfreezeUser_userNotExists_ReturnsNotFound() throws Exception {
        String email = "test@example.com";
        doThrow(new EntityNotFoundException("User not found with email: " + email))
                .when(usersServices).unfreezeUserByEmail(email);

        mockMvc.perform(put("/api/users/unfreeze").param("email", email))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found with email: " + email));

        verify(usersServices, times(1)).unfreezeUserByEmail(email);
    }

    @Test
    public void deleteUserByEmail_userExists_ReturnsSuccessMessage() throws Exception {
        String email = "test@example.com";
        doNothing().when(usersServices).deleteUserByEmail(email);

        mockMvc.perform(delete("/api/users/deleteByEmail").param("email", email))
                .andExpect(status().isOk())
                .andExpect(content().string("User with email " + email + " has been deleted."));

        verify(usersServices, times(1)).deleteUserByEmail(email);
    }

    @Test
    public void deleteUserByEmail_userNotExists_ReturnsNotFound() throws Exception {
        String email = "test@example.com";
        doThrow(new EntityNotFoundException("User not found with email: " + email))
                .when(usersServices).deleteUserByEmail(email);

        mockMvc.perform(delete("/api/users/deleteByEmail").param("email", email))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found with email: " + email));

        verify(usersServices, times(1)).deleteUserByEmail(email);
    }

    @Test
    public void resetPassword_userExists_ReturnsSuccessMessage() throws Exception {
        String email = "test@example.com";
        String newPassword = "newPassword";
        doNothing().when(usersServices).resetPassword(email, newPassword);

        mockMvc.perform(put("/api/users/resetPassword")
                        .param("email", email)
                        .param("newPassword", newPassword))
                .andExpect(status().isOk())
                .andExpect(content().string("Password for user with email " + email + " has been reset."));

        verify(usersServices, times(1)).resetPassword(email, newPassword);
    }

    @Test
    public void resetPassword_userNotExists_ReturnsNotFound() throws Exception {
        String email = "test@example.com";
        String newPassword = "newPassword";
        doThrow(new EntityNotFoundException("User not found with email: " + email))
                .when(usersServices).resetPassword(email, newPassword);

        mockMvc.perform(put("/api/users/resetPassword")
                        .param("email", email)
                        .param("newPassword", newPassword))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found with email: " + email));

        verify(usersServices, times(1)).resetPassword(email, newPassword);
    }

    @Test
    public void assignManagerByEmail_success_ReturnsSuccessMessage() throws Exception {
        String userEmail = "user@example.com";
        String managerEmail = "manager@example.com";
        doNothing().when(usersServices).assignManager(userEmail, managerEmail);

        mockMvc.perform(put("/api/users/assignManager")
                        .param("userEmail", userEmail)
                        .param("managerEmail", managerEmail))
                .andExpect(status().isOk())
                .andExpect(content().string("Manager assigned successfully."));

        verify(usersServices, times(1)).assignManager(userEmail, managerEmail);
    }

    @Test
    public void assignManagerByEmail_userNotExists_ReturnsNotFound() throws Exception {
        String userEmail = "user@example.com";
        String managerEmail = "manager@example.com";
        doThrow(new EntityNotFoundException("User not found with email: " + userEmail))
                .when(usersServices).assignManager(userEmail, managerEmail);

        mockMvc.perform(put("/api/users/assignManager")
                        .param("userEmail", userEmail)
                        .param("managerEmail", managerEmail))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found with email: " + userEmail));

        verify(usersServices, times(1)).assignManager(userEmail, managerEmail);
    }

    @Test
    public void assignManagerByEmail_managerNotExists_ReturnsNotFound() throws Exception {
        String userEmail = "user@example.com";
        String managerEmail = "manager@example.com";
        doThrow(new EntityNotFoundException("Manager not found with email: " + managerEmail))
                .when(usersServices).assignManager(userEmail, managerEmail);

        mockMvc.perform(put("/api/users/assignManager")
                        .param("userEmail", userEmail)
                        .param("managerEmail", managerEmail))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Manager not found with email: " + managerEmail));

        verify(usersServices, times(1)).assignManager(userEmail, managerEmail);
    }

}
