package com.microservice.email.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.email.models.EmailModel;

public interface EmailRepository extends JpaRepository<EmailModel, UUID>{
}
