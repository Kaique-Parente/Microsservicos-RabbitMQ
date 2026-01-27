package com.microservice.user.usecase;

import org.springframework.stereotype.Component;

import com.microservice.user.dto.CreateUserDto;
import com.microservice.user.dto.UserResponseDto;
import com.microservice.user.producers.UserEmailProducer;
import com.microservice.user.services.UserService;

@Component
public class CreateUserUseCase {
    
    private final UserService userService;
    private final UserEmailProducer emailProducer;

    public CreateUserUseCase(UserService userService, UserEmailProducer emailProducer){
        this.userService = userService;
        this.emailProducer = emailProducer;
    }

    public UserResponseDto execute(CreateUserDto userDto){
        var user = userService.createUser(userDto);
        emailProducer.publishMessageEmail(user);
        return userService.toResponse(user);
    }

}
