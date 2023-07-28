package com.neo.buysell.model.service;

import com.neo.buysell.model.dto.other.Credentials;
import com.neo.buysell.model.entity.Role;
import com.neo.buysell.model.entity.User;
import com.neo.buysell.model.exception.particular.EntityNotFound;
import com.neo.buysell.model.exception.particular.UserAlreadyExistsException;
import com.neo.buysell.model.exception.particular.WrongPasswordException;
import com.neo.buysell.model.service.special.SecurityUserService;
import com.neo.buysell.model.util.Paths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final SecurityUserService userService;
    private final PasswordEncoder encoder;
    private final RoleService roleService;

    private static final Logger LOG = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    public AuthService(SecurityUserService userService, PasswordEncoder encoder, RoleService roleService) {
        this.userService = userService;
        this.encoder = encoder;
        this.roleService = roleService;
    }

    public void login(Credentials credentials) {
        String username = credentials.username;
        Optional<User> optional = userService.getWrappedUser(username);
        if (optional.isEmpty()) {
            throw new EntityNotFound(User.class, HttpStatus.NOT_FOUND);
        }
        UserDetails userDetails = userService.loadUserByUsername(username);
        boolean matches = encoder.matches(credentials.password, userDetails.getPassword());
        if (!matches) {
            throw new WrongPasswordException(HttpStatus.UNAUTHORIZED, "Passwords do not match");
        }
    }

    public void register(Credentials credentials) {
        Optional<User> optional = userService.getWrappedUser(credentials.username);
        if (optional.isPresent()) {
            throw new UserAlreadyExistsException(HttpStatus.BAD_REQUEST);
        }
        if (credentials.password == null) {
            throw new WrongPasswordException(HttpStatus.BAD_REQUEST, "Password is not present");
        }
        createUser(credentials);
        LOG.info("User \"{}\" was registered", credentials.username);
    }

    private void createUser(Credentials credentials) {
        User user = new User();
        Role role = roleService.getRole(credentials.role);
        user.setPassword(encoder.encode(credentials.password));
        user.setUsername(credentials.username);
        user.setFirstName(credentials.firstName);
        user.setLastName(credentials.lastName);
        user.setPhone(credentials.phone);
        user.getRoles().add(role);
        user.setEmail(credentials.username);
        user.setAvatarPath(Paths.STANDARD_AVATAR_PATH);
        userService.saveUser(user);
    }

}

