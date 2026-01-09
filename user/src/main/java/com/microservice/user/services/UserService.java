package com.microservice.user.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservice.user.models.UserModel;
import com.microservice.user.repository.UserRepository;

@Service
public class UserService {
    
    final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Transactional
    public UserModel save(UserModel userModel){
        return userRepository.save(userModel);
    }

    public List<UserModel> getUsers(){
       return userRepository.findAll();
    }

}
