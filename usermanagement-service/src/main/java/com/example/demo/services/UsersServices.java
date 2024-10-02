package com.example.demo.services;


import com.example.demo.dtos.UsersDTO;
import com.example.demo.dtos.UsersSignUpDTO;
import com.example.demo.entities.Role;
import com.example.demo.entities.Titles;
import com.example.demo.entities.Users;
import com.example.demo.mappers.UsersMapper;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.TitlesRepository;
import com.example.demo.repositories.UsersRepository;
import exceptions.InvalidCredentialsException;
import exceptions.UserAlreadyExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsersServices {

    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    private final TitlesRepository titlesRepository;
    private final UsersMapper usersMapper;
    private final PasswordEncoder passwordEncoder;



    @Autowired
    public UsersServices(UsersRepository usersRepository, UsersMapper usersMapper, TitlesRepository titlesRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.usersRepository = usersRepository;
        this.usersMapper = usersMapper;
        this.titlesRepository = titlesRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public UsersDTO getUser(UUID id) {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));

        return usersMapper.toUsersDTO(user);
    }

    public UsersDTO login(String email, String password) {
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with email " + email + " not found"));

        if (!user.getPassword().equals(password)) {
            throw new InvalidCredentialsException("Invalid password");
        }

        return usersMapper.toUsersDTO(user);
    }

    public UsersSignUpDTO signUp(UsersSignUpDTO usersDTO) {
        if (usersRepository.existsByEmail(usersDTO.getEmail())) {
            throw new UserAlreadyExistsException("User with email " + usersDTO.getEmail() + " already exists.");
        }

        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new EntityNotFoundException("Role not found"));


        usersDTO.setPassword(passwordEncoder.encode(usersDTO.getPassword()));
        Users user = usersMapper.toUsers(usersDTO);
        user.setRoles(Collections.singleton(userRole));
        usersRepository.save(user);

        return usersMapper.toUsersSignupDTO(user);
    }


    public UsersDTO addUser(UsersDTO usersDTO) {
        if (usersRepository.existsByEmail(usersDTO.getEmail())) {
            throw new UserAlreadyExistsException("User with email " + usersDTO.getEmail() + " already exists.");
        }

        Map<String, Object> managerAndTitle = getManagerAndTitle(usersDTO);
        Users manager = (Users) managerAndTitle.get("manager");
        Titles title = (Titles) managerAndTitle.get("title");

        Users user = usersMapper.toUsers(usersDTO);

        user.setManager(manager);
        user.setTitleId(title);

        usersRepository.save(user);
        return usersMapper.toUsersDTO(user);
    }

    public UsersDTO updateUsers( UsersDTO usersUpdateDTO) {
        Map<String, Object> managerAndTitle = getManagerAndTitle(usersUpdateDTO);
        Users manager = (Users) managerAndTitle.get("manager");
        Titles title = (Titles) managerAndTitle.get("title");
        UUID id = usersUpdateDTO.getId();

        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));

        if(manager != null)
            user.setManager(manager);

        if(title != null)
            user.setTitleId(title);

        usersMapper.updateUsersFromDto(usersUpdateDTO,user);

        usersRepository.save(user);
        return usersMapper.toUsersDTO(user);
    }

    public void deleteUser(UUID id) {
        if (usersRepository.existsById(id)) {
            usersRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("User with id " + id + " does not exist.");
        }
    }


    public Page<UsersDTO> getAllUsers(Pageable pageable) {
        Page<Users> usersPage = usersRepository.findAll(pageable);

        if (usersPage.isEmpty()) {
            throw new EntityNotFoundException("No users found");
        }

        return usersPage.map(usersMapper::toUsersDTO);

    }

    public Map<String, Object> getManagerAndTitle(UsersDTO usersDTO) {
        Map<String, Object> result = new HashMap<>();

        Users manager = null;
        if (usersDTO.getManagerId() != null) {
            manager = usersRepository.findById(usersDTO.getManagerId())
                    .orElseThrow(() -> new EntityNotFoundException("Manager not found"));
        }
        result.put("manager", manager);

        Titles title = null;
        if (usersDTO.getTitleId() != null) {
            title = titlesRepository.findById(usersDTO.getTitleId())
                    .orElseThrow(() -> new EntityNotFoundException("Title not found"));
        }
        result.put("title", title);

        return result;
    }




}
