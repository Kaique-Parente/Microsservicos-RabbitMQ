package com.microservice.user.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import com.microservice.user.dto.CreateUserDto;
import com.microservice.user.mapper.UserMapper;
import com.microservice.user.models.RoleModel;
import com.microservice.user.models.UserModel;
import com.microservice.user.repository.RoleRepository;
import com.microservice.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    BCryptPasswordEncoder passwordEncoder;

    @Mock
    UserMapper userMapper;

    @InjectMocks
    UserService userService;

    @Test
    void shouldThrowExceptionWhenUserEmailAlreadyExists() {

        var userDto = new CreateUserDto("TestName", "email@email.com", "333");

        when(roleRepository.findByName("BASIC"))
            .thenReturn(Optional.of(new RoleModel()));
        
        when(userRepository.findByEmail(userDto.email()))
            .thenReturn(Optional.of(new UserModel()));

        assertThrows(ResponseStatusException.class, () -> {
            userService.createUser(userDto);
        });

        verify(roleRepository).findByName("BASIC");
        verify(userRepository).findByEmail(userDto.email());
    }

}
