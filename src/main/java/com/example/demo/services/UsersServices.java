package com.example.demo.services;

import com.example.demo.exceptions.UserAlreadyExistsException;
import com.example.demo.dtos.UsersDTO;
import com.example.demo.entities.Titles;
import com.example.demo.entities.Users;
import com.example.demo.mappers.UsersMapper;
import com.example.demo.repositories.TitlesRepository;
import com.example.demo.repositories.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsersServices {

    private final UsersRepository usersRepository;
    private final TitlesRepository titlesRepository;
    private final UsersMapper usersMapper;


    @Autowired
    public UsersServices(UsersRepository usersRepository, UsersMapper usersMapper, TitlesRepository titlesRepository) {
        this.usersRepository = usersRepository;
        this.usersMapper = usersMapper;
        this.titlesRepository = titlesRepository;
    }

    public UsersDTO getUser(UUID id) {
        Users user = usersRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + id + " not found"));

        return usersMapper.toUsersDTO(user);
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

    public UsersDTO updateUsers(UUID id, UsersDTO usersUpdateDTO) {
        Map<String, Object> managerAndTitle = getManagerAndTitle(usersUpdateDTO);
        Users manager = (Users) managerAndTitle.get("manager");
        Titles title = (Titles) managerAndTitle.get("title");

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
