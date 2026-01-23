package com.microservice.user.dto;

public record LoginResponseDto(String accessToken,
                               Long expiresIn) {
}
