package com.example.demo.mappers;

import com.example.demo.dtos.UsersDTO;
import com.example.demo.entities.Titles;
import com.example.demo.entities.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.UUID;

@Mapper(componentModel ="spring")
public interface UsersMapper {

    @Mapping(source = "manager.id", target = "managerId")
    @Mapping(source = "titleId.id", target = "titleId")
    UsersDTO toUsersDTO(Users users);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "manager", ignore = true)
    @Mapping(target = "titleId", ignore = true)
    Users toUsers(UsersDTO usersDTO);

    void updateUsersFromDto(UsersDTO usersDTO, @MappingTarget Users users);


}

