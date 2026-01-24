package com.microservice.user.dto;

import java.util.Set;

public record UserResponseDto (String name,
                               String email,
                               Set<String> roles) {
}
