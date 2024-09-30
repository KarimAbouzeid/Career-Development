package com.example.demo.mappers;

import com.example.demo.dtos.UsersDTO;
import com.example.demo.dtos.UsersSignUpDTO;
import com.example.demo.entities.Users;
import org.mapstruct.*;

@Mapper(componentModel ="spring")
public interface UsersMapper {

    @Mapping(source = "manager.id", target = "managerId")
    @Mapping(source = "titleId.id", target = "titleId")
    UsersDTO toUsersDTO(Users users);


    @Mapping(target = "id", ignore = true)
    @Mapping(target = "manager", ignore = true)
    @Mapping(target = "titleId", ignore = true)
    Users toUsers(UsersDTO usersDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "manager", ignore = true)
    @Mapping(target = "titleId", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUsersFromDto(UsersDTO usersDTO, @MappingTarget Users users);


    UsersSignUpDTO toUsersSignupDTO(Users users);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "manager", ignore = true)
    @Mapping(target = "titleId", ignore = true)
    Users toUsers(UsersSignUpDTO usersSignUpDTO);


}

