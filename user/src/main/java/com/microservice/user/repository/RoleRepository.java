package com.microservice.user.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservice.user.models.RoleModel;

@Repository
public interface RoleRepository extends JpaRepository<RoleModel,UUID>{

    Optional<RoleModel> findByName(String name);
    
}
