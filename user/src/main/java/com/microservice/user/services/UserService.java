package com.microservice.user.services;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.microservice.user.dto.CreateUserDto;
import com.microservice.user.mapper.UserMapper;
import com.microservice.user.models.RoleModel;
import com.microservice.user.models.UserModel;
import com.microservice.user.repository.RoleRepository;
import com.microservice.user.repository.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder, UserMapper userMapper){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Transactional
    public UserModel createUser(CreateUserDto userDto){
        var basicRole = roleRepository.findByName(RoleModel.Values.BASIC.name()).get();

        var userDB = userRepository.findByEmail(userDto.email());

        if (userDB.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Esse email já existe no nosso sistema!");
        }

        var user = userMapper.toUserEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.password()));
        user.setRoles(Set.of(basicRole));

        return userRepository.save(user);
    }

    public List<UserModel> listAll(){
       return userRepository.findAll();
    }

}
