package com.microservice.user.models;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TB_ROLES")
@Getter
@Setter
public class RoleModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private UUID roleId;

    private String name;

    public enum Values {
        
        ADMIN(1L),
        BASIC(2L);

        Long roleId;

        Values(Long roleId){
            this.roleId = roleId;
        }

        public Long getRoleId(){
            return roleId;
        }
    }
    
}
