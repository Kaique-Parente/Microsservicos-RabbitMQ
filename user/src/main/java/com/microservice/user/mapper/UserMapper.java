package com.microservice.user.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.microservice.user.dto.CreateUserDto;
import com.microservice.user.dto.UserResponseDto;
import com.microservice.user.models.RoleModel;
import com.microservice.user.models.UserModel;

@Mapper(componentModel = "spring")
public interface UserMapper {
    
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    UserModel toUserEntity(CreateUserDto userDto);

    @Mapping(target = "roles", source = "roles", qualifiedByName = "rolesToString")
    UserResponseDto toUserResponse(UserModel userModel);

    @Named("rolesToString")
    default Set<String> mapRoles(Set<RoleModel> roles){
        return roles.stream()
                .map(RoleModel::getName)
                .collect(Collectors.toSet());
    }
}
