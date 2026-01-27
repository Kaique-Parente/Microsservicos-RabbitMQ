package com.microservice.user.services;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.microservice.user.dto.CreateUserDto;
import com.microservice.user.dto.UserResponseDto;
import com.microservice.user.models.RoleModel;
import com.microservice.user.models.UserModel;
import com.microservice.user.repository.RoleRepository;
import com.microservice.user.repository.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserModel createUser(CreateUserDto userDto){
        var basicRole = roleRepository.findByName(RoleModel.Values.BASIC.name()).get();

        var userDB = userRepository.findByEmail(userDto.email());

        if (userDB.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Esse email já existe no nosso sistema!");
        }

        var user = UserModel.builder()
            .name(userDto.name())
            .email(userDto.email())
            .password(passwordEncoder.encode(userDto.password()))
            .roles(Set.of(basicRole))
            .build();

        return userRepository.save(user);
    }

    public UserResponseDto toResponse(UserModel userModel){
         Set<String> roles = userModel.getRoles().stream()
                    .map(RoleModel::getName)
                    .collect(Collectors.toSet());

        return new UserResponseDto(userModel.getName(), userModel.getEmail(), roles);
    }

    public List<UserModel> listAll(){
       return userRepository.findAll();
    }

}
