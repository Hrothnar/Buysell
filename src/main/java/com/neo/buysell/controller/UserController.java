package com.neo.buysell.controller;

import com.neo.buysell.model.dto.PasswordRequest;
import com.neo.buysell.model.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(value = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping("/set_password")
    public PasswordRequest setPassword(@RequestBody PasswordRequest passwordRequest) {
        //logic
        return new PasswordRequest();
    }

    @GetMapping("/me")
    public UserDTO getUser(HttpServletRequest request) {
        //logic
        return new UserDTO();
    }

    @PatchMapping("/me")
    public UserDTO updateInfo(HttpServletRequest request, @RequestBody UserDTO userDTO) {
        //logic
        return userDTO;
    }

    @PatchMapping("/me/image")
    public ResponseEntity updateAvatar(@RequestParam MultipartFile file) {
        //logic
        return ResponseEntity.ok().build();
    }




}
