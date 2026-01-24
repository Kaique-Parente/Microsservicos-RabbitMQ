package com.microservice.user.configs;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.microservice.user.models.RoleModel;
import com.microservice.user.models.UserModel;
import com.microservice.user.repository.RoleRepository;
import com.microservice.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Configuration
public class AdminUserConfig implements CommandLineRunner {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public AdminUserConfig(RoleRepository roleRepository,
            UserRepository userRepository,
            BCryptPasswordEncoder passwordEncoder) {

        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        var roleAdmin = roleRepository.findByName(RoleModel.Values.ADMIN.name()).get();

        var userAdmin = userRepository.findByEmail("root@adminservice.com");

        userAdmin.ifPresentOrElse(
                user -> System.out.println("admin já existe"),
                () -> {
                    var newUser = new UserModel();
                    newUser.setEmail("root@adminservice.com");
                    newUser.setName("root");
                    newUser.setPassword(passwordEncoder.encode("333"));
                    newUser.setRoles(Set.of(roleAdmin));
                    userRepository.save(newUser);
                });

    }
}
