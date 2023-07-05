package com.neo.buysell.model.service;

import com.neo.buysell.model.dto.RegisterRequest;
import com.neo.buysell.model.enumeration.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImp implements com.neo.buysell.model.service.Interfaccia.AuthService {
    private final UserDetailsManager manager;
    private final PasswordEncoder encoder;

    @Autowired
    public AuthServiceImp(UserDetailsManager manager, PasswordEncoder encoder) {
        this.manager = manager;
        this.encoder = encoder;
    }

    @Override
    public boolean login(String username, String password) {
        if (!manager.userExists(username)) {
            return false;
        }
        UserDetails userDetails = manager.loadUserByUsername(username);
        return encoder.matches(password, userDetails.getPassword());
    }

    @Override
    public boolean register(RegisterRequest registerRequest, Role role) {
        if (manager.userExists(registerRequest.getUsername())) {
            return false;
        }
        manager.createUser(User.builder()
                .passwordEncoder(this.encoder::encode)
                .password(registerRequest.getPassword())
                .username(registerRequest.getUsername())
                .roles(role.name())
                .build());
        return true;
    }
}
