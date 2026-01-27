package com.microservice.user.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.user.dto.CreateUserDto;
import com.microservice.user.dto.UserResponseDto;
import com.microservice.user.models.UserModel;
import com.microservice.user.services.UserService;
import com.microservice.user.usecase.CreateUserUseCase;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final CreateUserUseCase createUserUseCase;

    public UserController(UserService userService, CreateUserUseCase createUserUseCase){
        this.userService = userService;
        this.createUserUseCase = createUserUseCase;
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> saveUser(@RequestBody @Valid CreateUserDto userRecordDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(createUserUseCase.execute(userRecordDto));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<UserModel>> listAll(){
        var users = userService.listAll();
        return ResponseEntity.ok(users);
    }
}
