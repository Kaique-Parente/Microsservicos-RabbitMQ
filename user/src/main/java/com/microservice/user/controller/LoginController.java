package com.microservice.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.user.dto.LoginRequestDto;
import com.microservice.user.dto.LoginResponseDto;
import com.microservice.user.services.LoginService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class LoginController {
    
    private final LoginService loginService;

    public LoginController(LoginService loginService){
        this.loginService = loginService;
    }

    @PostMapping
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginRequestDto loginRequestDTO){
        return ResponseEntity.ok(loginService.login(loginRequestDTO));
    }

}
