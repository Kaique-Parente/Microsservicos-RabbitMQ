package com.microservice.user.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import com.microservice.user.dto.LoginResponseDto;
import com.microservice.user.models.RoleModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.server.ResponseStatusException;

import com.microservice.user.dto.LoginRequestDto;
import com.microservice.user.models.UserModel;
import com.microservice.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    JwtEncoder jwtEncoder;

    @InjectMocks
    LoginService loginService;

    @Test
    void shouldThrowExceptionWhenUserEmailNotExists(){

        var requestDto = new LoginRequestDto("email@email.com", "333");

        when(userRepository.findByEmail(requestDto.email()))
            .thenReturn(Optional.empty());
        
        assertThrows(ResponseStatusException.class, () -> {
            loginService.login(requestDto);
        });

    }

    @Test
    void shouldThrowExceptionWhenPasswordIsIncorrect() {

        var user = UserModel.builder()
                .name("TesteName")
                .email("email@mail.com")
                .password(passwordEncoder.encode("333"))
                .build();

        var requestDto = new LoginRequestDto("email@mail.com", "111111");

        when(userRepository.findByEmail(requestDto.email()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(requestDto.password(), user.getPassword()))
                .thenReturn(false);

        assertThrows(ResponseStatusException.class, () -> {
            loginService.login(requestDto);
        });

    }

    @Test
    void shouldLoginSuccessfullyAndReturnJwt() {

        var requestDto = new LoginRequestDto("email@email.com", "333");

        var user = UserModel.builder()
                .userId(UUID.randomUUID())
                .email(requestDto.email())
                .password("hashed-password")
                .roles(Set.of(new RoleModel()))
                .build();

        when(userRepository.findByEmail(requestDto.email()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(requestDto.password(), user.getPassword()))
                .thenReturn(true);

        Jwt jwt = Jwt.withTokenValue("fake-jwt-token")
                .header("alg", "none")
                .claim("scope", "BASIC")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(300))
                .build();

        when(jwtEncoder.encode(any()))
                .thenReturn(jwt);

        LoginResponseDto response = loginService.login(requestDto);

        assertNotNull(response);
        assertEquals("fake-jwt-token", response.accessToken());
        assertEquals(300L, response.expiresIn());
    }
}
