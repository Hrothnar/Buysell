package com.neo.buysell.model.service;

import com.neo.buysell.model.dto.TRegister;
import com.neo.buysell.model.enumeration.Role;
import com.neo.buysell.model.service.contract.AuthServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

@Service
public class AuthService implements AuthServiceInt {
    private final UserDetailsManager manager;
    private final PasswordEncoder encoder;

    @Autowired
    public AuthService(UserDetailsManager manager, PasswordEncoder encoder) {
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
    public boolean register(TRegister tRegister, Role role) {
        if (manager.userExists(tRegister.getUsername())) {
            return false;
        }
        manager.createUser(User.builder()
                .passwordEncoder(this.encoder::encode)
                .password(tRegister.getPassword())
                .username(tRegister.getUsername())
                .roles(role.name())
                .build());
        return true;
    }
}
