package com.forkify_backend.service.dataInitializer;

import com.forkify_backend.persistence.entity.Role;
import com.forkify_backend.persistence.entity.User;
import com.forkify_backend.persistence.repository.RoleRepository;
import com.forkify_backend.persistence.repository.UserRepository;
import com.forkify_backend.security.RoleConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.count() == 0) {
            Role userRole = Role.builder().name(RoleConstants.USER).build();
            roleRepository.save(userRole);

            Role adminRole = Role.builder().name(RoleConstants.ADMIN).build();
            roleRepository.save(adminRole);
        }

        if (userRepository.count() == 0) {
            Optional<Role> userRole = roleRepository.findByName(RoleConstants.USER);
            Optional<Role> adminRole = roleRepository.findByName(RoleConstants.ADMIN);
            if (userRole.isEmpty() || adminRole.isEmpty()) {
                throw new IllegalArgumentException();
            }
            Set<Role> roles = Set.of(userRole.get(), adminRole.get());
            User user = User.builder().userId("eFPeMgXcTSX4cOgxwS8SPae72Js1")
                    .username("Yann")
                    .email("yann.ballanger@gmail.com")
                    .roles(roles)
                    .build();
            userRepository.save(user);
        }
    }
}
