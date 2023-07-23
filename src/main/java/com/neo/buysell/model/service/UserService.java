package com.neo.buysell.model.service;

import com.neo.buysell.model.dto.*;
import com.neo.buysell.model.entity.Ad;
import com.neo.buysell.model.entity.User;
import com.neo.buysell.model.exception.particular.EntityNotFound;
import com.neo.buysell.model.exception.particular.RuntimeIOException;
import com.neo.buysell.model.repository.UserRepository;
import com.neo.buysell.model.util.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public Optional<User> getWrappedUser(String username) {
        return userRepository.findByUsername(username);
    }

    public User getUser(long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFound(User.class, HttpStatus.NOT_FOUND));
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFound(User.class, HttpStatus.NOT_FOUND));
    }

    public UserDTO getUserDTO(Authentication authentication) {
        User user = getUser(authentication.getName());
        return UserDTO.toUserDto(user);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void setPassword(PasswordUpdaterDTO passwordUpdaterDTO, Authentication authentication) {
        String username = authentication.getName();
        User user = getUser(username);
        String encodedPassword = encoder.encode(passwordUpdaterDTO.newPassword);
        user.setPassword(encodedPassword);
        save(user);
    }

    public ImageDTO getAvatar(Authentication authentication) {
        String username = authentication.getName();
        User user = getUser(username);
        String avatarPath = user.getAvatarPath();
        File file = new File(avatarPath);
        byte[] bytes;
        try (FileInputStream fis = new FileInputStream(file)) {
            bytes = fis.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeIOException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        MediaType mediaType = ImageDTO.toMediatype(avatarPath);
        return new ImageDTO(bytes, mediaType);
    }

    public void updateInfo(UserUpdaterDTO userUpdaterDTO, Authentication authentication) {
        User user = getUser(authentication.getName());
        user.setFirstName(userUpdaterDTO.firstName);
        user.setLastName(userUpdaterDTO.lastName);
        user.setPhone(userUpdaterDTO.phone);
        save(user);
    }

    public byte[] updateAvatar(MultipartFile file, Authentication authentication) {
        User user = getUser(authentication.getName());
        byte[] bytes;
        String avatarPath = user.getAvatarPath();
        Path path = Path.of(avatarPath);
        try {
            if (Files.exists(path) && !avatarPath.equals(Paths.STANDARD_AVATAR_PATH)) {
                Files.delete(path);
            }
            bytes = setAvatar(user, file);
            save(user);
        } catch (IOException e) {
            throw new RuntimeIOException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return bytes;
    }


    private byte[] setAvatar(User user, MultipartFile file) {
        byte[] bytes;
        Path directory = Path.of(Paths.AVATAR_DIRECTORY);
        try {
            if (!Files.exists(directory)) {
                Files.createDirectory(directory);
            }
            String uuid = UUID.randomUUID().toString().substring(0, 4);
            bytes = file.getBytes();
            Path path = Path.of(Paths.AVATAR_DIRECTORY + uuid + file.getOriginalFilename());
            Files.write(path, bytes);
            user.setAvatarPath(path.toAbsolutePath().toString());
        } catch (IOException e) {
            throw new RuntimeIOException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return bytes;
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
