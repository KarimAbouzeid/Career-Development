package com.example.demo.mappers;

import com.example.demo.dtos.UsersDTO;
import com.example.demo.entities.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel ="spring")
public interface UsersMapper {

    UsersDTO toUsers(Users users);

    @Mapping(target = "id", ignore = true)
    Users toUsersDTO(UsersDTO usersDTO);

}

