package com.neo.buysell.controller;

import com.neo.buysell.model.dto.TPassword;
import com.neo.buysell.model.dto.TUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(value = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping("/users")
public class UserController {

    @PostMapping("/set_password")
    public TPassword setPassword(@RequestBody TPassword tPassword) {
        //logic
        return new TPassword();
    }

    @GetMapping("/me")
    public TUser getUser(HttpServletRequest request) {
        //logic
        return new TUser();
    }

    @PatchMapping("/me")
    public TUser updateInfo(HttpServletRequest request, @RequestBody TUser tUser) {
        //logic
        return tUser;
    }

    @PatchMapping("/me/image")
    public ResponseEntity updateAvatar(@RequestParam MultipartFile file) {
        //logic
        return ResponseEntity.ok().build();
    }




}
