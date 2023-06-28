package com.neo.buysell.controller;

import com.neo.buysell.model.dto.TLogin;
import com.neo.buysell.model.dto.TRegister;
import com.neo.buysell.model.enumeration.Role;
import com.neo.buysell.model.service.contract.AuthServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(value = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
public class AuthController {
    private final AuthServiceInt authService;

    @Autowired
    public AuthController(AuthServiceInt authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody TLogin tLogin) {
        if (authService.login(tLogin.getUsername(), tLogin.getPassword())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody TRegister tRegister) {
        Role role = tRegister.getRole() == null ? Role.USER : tRegister.getRole();
        if (authService.register(tRegister, role)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
