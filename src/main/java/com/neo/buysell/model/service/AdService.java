package com.neo.buysell.model.service;

import com.neo.buysell.model.dto.*;
import com.neo.buysell.model.entity.Ad;
import com.neo.buysell.model.entity.Comment;
import com.neo.buysell.model.entity.User;
import com.neo.buysell.model.exception.particular.EntityNotFound;
import com.neo.buysell.model.exception.particular.RuntimeIOException;
import com.neo.buysell.repository.AdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AdService {
    private final static String IMAGE_PATH = "images/";
    private final static String AVATAR_PATH = "avatars/";

    private final AdRepository adRepository;
    private final CommentService commentService;
    private final UserService userService;

    @Autowired
    public AdService(AdRepository adRepository, CommentService commentService, UserService userService) {
        this.adRepository = adRepository;
        this.commentService = commentService;
        this.userService = userService;

//        User user = new User();
//        user.setPhone("+7888989898");
//        user.setFirstName("Dude");
//        user.setLastName("Dudov");
//        user.setEmail("dude@mail.ru");
//        user.setAvatarPath("D:\\Instruments\\Projects\\IDEA\\Buysell\\avatars\\avatar.jpg");
//        userService.save(user);
    }

    public Ad getAd(long id) {
        return adRepository.findById(id).orElseThrow(() -> new EntityNotFound(Ad.class, HttpStatus.NOT_FOUND));
    }

    public AdsDTO getAllAds() {
        List<Ad> ads = adRepository.findAll();
        ArrayList<AdDTO> adDTOs = ads.stream()
                .map(ad -> AdDTO.toDto(ad))
                .collect(Collectors.toCollection(() -> new ArrayList<>()));
        return new AdsDTO(adDTOs.size(), adDTOs);
    }

    public ExtendedAdDTO getAdDTO(long id) {
        Ad ad = getAd(id);
        return ExtendedAdDTO.toDto(ad);
    }

    public void removeAd(long id) {
        Ad ad = getAd(id);
        adRepository.delete(ad);
    }

    public AdDTO updateAd(long id, AdUpdaterDTO adUpdaterDTO) {
        Ad ad = getAd(id);
        ad.setPrice(adUpdaterDTO.price);
        ad.setTitle(adUpdaterDTO.title);
        ad.setDescription(adUpdaterDTO.description);
        adRepository.save(ad);
        return AdDTO.toDto(ad);
    }

    public byte[] updateAd(long id, MultipartFile file) {
        Ad ad = getAd(id);
        byte[] bytes;
        Path path = Path.of(ad.getImagePath());
        try {
            if (Files.exists(path)) {
                Files.delete(path);
            }
            bytes = setAdImage(ad, file);
            adRepository.save(ad);
        } catch (IOException e) {
            throw new RuntimeIOException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return bytes;
    }

    public CommentsDTO getAdComments(long id) {
        Ad ad = getAd(id);
        ArrayList<CommentDTO> commentDTOs = ad.getComments().stream()
                .map(comment -> CommentDTO.toDto(comment))
                .collect(Collectors.toCollection(() -> new ArrayList<>()));
        return new CommentsDTO(commentDTOs.size(), commentDTOs);
    }

    public CommentDTO addCommentToAd(long id, CommentUpdaterDTO commentUpdaterDTO) {
        Ad ad = getAd(id);
        Comment comment = new Comment();
        comment.setText(commentUpdaterDTO.text);
        comment.setCreationTime(System.currentTimeMillis());
        ad.addComment(comment);
        commentService.save(comment);
        adRepository.save(ad);
        return CommentDTO.toDto(comment);
    }

    public void removeComment(long id, long commentId) {
        Comment comment = commentService.getComment(commentId);
        commentService.remove(comment);
    }

    public CommentDTO updateComment(long id, long commentId, CommentUpdaterDTO commentUpdaterDTO) {
        Ad ad = getAd(id);
        Comment comment = commentService.getComment(commentId);
        comment.setText(commentUpdaterDTO.text);
        commentService.save(comment);
        adRepository.save(ad);
        return CommentDTO.toDto(comment);
    }

    public AdDTO addAd(AdUpdaterDTO adUpdaterDTO, MultipartFile file) {
        Ad ad = new Ad();
        User user = userService.getUser(1L);
        ad.setDescription(adUpdaterDTO.description);
        ad.setTitle(adUpdaterDTO.title);
        ad.setPrice(adUpdaterDTO.price);
        setAdImage(ad, file);
        user.addAd(ad);
        adRepository.save(ad);
        return AdDTO.toDto(ad);
    }

    private byte[] setAdImage(Ad ad, MultipartFile file) {
        byte[] bytes;
        Path directory = Path.of(IMAGE_PATH);
        try {
            if (!Files.exists(directory)) {
                Files.createDirectory(directory);
            }
            String uuid = UUID.randomUUID().toString().substring(0, 4);
            bytes = file.getBytes();
            Path path = Path.of(IMAGE_PATH + uuid + file.getOriginalFilename());
            Files.write(path, bytes);
            ad.setImagePath(path.toAbsolutePath().toString());
        } catch (IOException e) {
            throw new RuntimeIOException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return bytes;
    }

    private void setAvatar(User user, MultipartFile file) {
        Path directory = Path.of(AVATAR_PATH);
        try {
            if (!Files.exists(directory)) {
                Files.createDirectory(directory);
            }
            String uuid = UUID.randomUUID().toString().substring(0, 4);
            Path path = Path.of(AVATAR_PATH + uuid + file.getOriginalFilename());
            Files.write(path, file.getBytes());
            user.setAvatarPath(path.toAbsolutePath().toString());
        } catch (IOException e) {
            throw new RuntimeIOException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public byte[] getImageBytes(long id) {
        Ad ad = getAd(id);
        String imagePath = ad.getImagePath();
        File file = new File(imagePath);
        byte[] bytes;
        try {
            FileInputStream fis = new FileInputStream(file);
            bytes = fis.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeIOException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return bytes;
    }


}
