package com.microservice.user.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.microservice.user.dto.CreateUserDto;
import com.microservice.user.dto.UserResponseDto;
import com.microservice.user.mapper.UserMapper;
import com.microservice.user.models.UserModel;
import com.microservice.user.producers.UserEmailProducer;
import com.microservice.user.services.UserService;

@ExtendWith(MockitoExtension.class)
class CreateUserUseCaseTest {
    
    @Mock
    UserService userService;

    @Mock
    UserEmailProducer emailProducer;

    @Mock
    UserMapper userMapper;

    @InjectMocks
    CreateUserUseCase createUserUseCase;

    @Test
    void shouldCreateNewUserAndPublishMessage() {

        var userDto = new CreateUserDto("Teste", "email@email.com", "33333");

        var userEntity = UserModel.builder()
            .name("Teste")
            .email("email@email.com")
            .password("hashed")
            .build();

        Set<String> roles = new HashSet<String>();

        var response = new UserResponseDto(
            userEntity.getName(),
            userEntity.getEmail(),
            roles
        );

        when(userService.createUser(userDto)).thenReturn(userEntity);
        when(userMapper.toUserResponse(userEntity)).thenReturn(response);
       
        UserResponseDto result = createUserUseCase.execute(userDto);

        verify(userService).createUser(userDto);
        verify(emailProducer).publishMessageEmail(userEntity);
        verify(userMapper).toUserResponse(userEntity);

        assertEquals(response, result);
    }

}
