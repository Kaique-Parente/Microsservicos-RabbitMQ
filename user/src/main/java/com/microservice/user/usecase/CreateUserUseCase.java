package com.microservice.user.usecase;

import org.springframework.stereotype.Component;

import com.microservice.user.dto.CreateUserDto;
import com.microservice.user.dto.UserResponseDto;
import com.microservice.user.mapper.UserMapper;
import com.microservice.user.producers.UserEmailProducer;
import com.microservice.user.services.UserService;

@Component
public class CreateUserUseCase {
    
    private final UserService userService;
    private final UserEmailProducer emailProducer;
    private final UserMapper userMapper;

    public CreateUserUseCase(UserService userService, UserEmailProducer emailProducer, UserMapper userMapper){
        this.userService = userService;
        this.emailProducer = emailProducer;
        this.userMapper = userMapper;
    }

    public UserResponseDto execute(CreateUserDto userDto){
        var user = userService.createUser(userDto);
        emailProducer.publishMessageEmail(user);
        return userMapper.toUserResponse(user);
    }

}
