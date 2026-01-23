package com.microservice.user.dto;

public record LoginResponseDTO(String accessToken,
                               Long expiresIn) {
}
