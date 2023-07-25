package com.neo.buysell.model.service;

import com.neo.buysell.model.dto.*;
import com.neo.buysell.model.entity.Ad;
import com.neo.buysell.model.entity.User;
import com.neo.buysell.model.exception.particular.EntityNotFound;
import com.neo.buysell.model.repository.UserRepository;
import com.neo.buysell.model.util.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final FileService fileService;
    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, FileService fileService, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.fileService = fileService;
        this.encoder = encoder;
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFound(User.class, HttpStatus.NOT_FOUND));
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void setPassword(PasswordUpdaterDTO passwordUpdaterDTO, Authentication authentication) {
        User user = getUser(authentication.getName());
        String encodedPassword = encoder.encode(passwordUpdaterDTO.newPassword);
        user.setPassword(encodedPassword);
        saveUser(user);
    }

    public void updateInfo(UserUpdaterDTO userUpdaterDTO, Authentication authentication) {
        User user = getUser(authentication.getName());
        user.setFirstName(userUpdaterDTO.firstName);
        user.setLastName(userUpdaterDTO.lastName);
        user.setPhone(userUpdaterDTO.phone);
        saveUser(user);
    }

    public ImageDTO getAvatar(Authentication authentication) {
        User user = getUser(authentication.getName());
        return fileService.readImage(user.getAvatarPath());
    }

    public ImageDTO updateAvatar(MultipartFile file, Authentication authentication) {
        User user = getUser(authentication.getName());
        fileService.removeImage(user.getAvatarPath());
        ImageDTO imageDTO = fileService.writeImage(file, Paths.AVATAR_DIRECTORY);
        user.setAvatarPath(imageDTO.path);
        saveUser(user);
        return imageDTO;
    }

    public AdsDTO getUserAds(Authentication authentication) {
        User user = getUser(authentication.getName());
        Set<Ad> ads = user.getAds();
        LinkedList<AdDTO> adDTOs = ads.stream()
                .map(AdDTO::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
        return new AdsDTO(adDTOs.size(), adDTOs);
    }
}
