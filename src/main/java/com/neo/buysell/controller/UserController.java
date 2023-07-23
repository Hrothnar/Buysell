package com.neo.buysell.controller;

import com.neo.buysell.model.dto.ImageDTO;
import com.neo.buysell.model.dto.PasswordUpdaterDTO;
import com.neo.buysell.model.dto.UserDTO;
import com.neo.buysell.model.dto.UserUpdaterDTO;
import com.neo.buysell.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    public ResponseEntity<?> setPassword(@RequestBody PasswordUpdaterDTO passwordUpdaterDTO, Authentication authentication) {
//        if (!authentication.isAuthenticated()) {
//            throw new NotAuthenticatedException(HttpStatus.UNAUTHORIZED);
//        }
        userService.setPassword(passwordUpdaterDTO, authentication);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public UserDTO getUser(Authentication authentication) {
//        if (!authentication.isAuthenticated()) {
//            throw new NotAuthenticatedException(HttpStatus.UNAUTHORIZED);
//        }
        UserDTO userDTO = userService.getUserDTO(authentication);
        return userDTO;
    }

    @PatchMapping("/me")
    public UserUpdaterDTO updateInfo(@RequestBody UserUpdaterDTO userUpdaterDTO, Authentication authentication) {
//        if (!authentication.isAuthenticated()) {
//            throw new NotAuthenticatedException(HttpStatus.UNAUTHORIZED);
//        }
        userService.updateInfo(userUpdaterDTO, authentication);
        return userUpdaterDTO;
    }

    @PatchMapping("/me/image")
    public ResponseEntity<?> updateAvatar(@RequestParam("image") MultipartFile file, Authentication authentication) {
//        if (!authentication.isAuthenticated()) {
//            throw new NotAuthenticatedException(HttpStatus.UNAUTHORIZED);
//        }
        userService.updateAvatar(file, authentication);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me/image")
    public ResponseEntity<byte[]> getAvatar(Authentication authentication) {
//        if (!authentication.isAuthenticated()) {
//            throw new NotAuthenticatedException(HttpStatus.UNAUTHORIZED);
//        }
        ImageDTO avatar = userService.getAvatar(authentication);
        byte[] bytes = avatar.bytes;
        return ResponseEntity.ok()
                .contentLength(bytes.length)
                .contentType(avatar.mediaType)
                .body(bytes);
    }


}
