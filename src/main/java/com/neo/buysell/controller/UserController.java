package com.neo.buysell.controller;

import com.neo.buysell.model.dto.PasswordRequest;
import com.neo.buysell.model.dto.UserDTO;
import com.neo.buysell.model.dto.UserUpdaterDTO;
import com.neo.buysell.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(value = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/set_password")
    public ResponseEntity<?> setPassword(@RequestBody PasswordRequest passwordRequest) {
        //logic нужны данные авторизации
        return ResponseEntity.ok().build(); //200 | 401 Unauthorized | 403 Forbidden
    }

    @GetMapping("/me")
    public UserDTO getUser() {
        //logic нужны данные авторизации
        return new UserDTO(); // 401 Unauthorized
    }

    @PatchMapping("/me")
    public UserUpdaterDTO updateInfo(@RequestBody UserUpdaterDTO userUpdaterDTO) {
        //logic нужны данные авторизации
        return new UserUpdaterDTO(); // 401 Unauthorized
    }

    @PatchMapping("/me/image")
    public ResponseEntity<?> updateAvatar(@RequestParam("image") MultipartFile file) {
        //logic нужны данные авторизации
        return ResponseEntity.ok().build(); // 401 Unauthorized
    }




}
