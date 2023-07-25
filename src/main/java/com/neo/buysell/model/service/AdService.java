package com.neo.buysell.model.service;

import com.neo.buysell.model.dto.*;
import com.neo.buysell.model.entity.Ad;
import com.neo.buysell.model.entity.Comment;
import com.neo.buysell.model.entity.User;
import com.neo.buysell.model.exception.particular.EntityNotFound;
import com.neo.buysell.model.repository.AdRepository;
import com.neo.buysell.model.util.Paths;
import com.neo.buysell.model.util.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdService {

    private final AdRepository adRepository;
    private final CommentService commentService;
    private final UserService userService;
    private final FileService fileService;

    @Autowired
    public AdService(AdRepository adRepository, CommentService commentService, UserService userService, FileService fileService) {
        this.adRepository = adRepository;
        this.commentService = commentService;
        this.userService = userService;
        this.fileService = fileService;
    }

    public Ad getAd(long id) {
        return adRepository.findById(id).orElseThrow(() -> new EntityNotFound(Ad.class, HttpStatus.NOT_FOUND));
    }

    public void saveAd(Ad ad) {
        adRepository.save(ad);
    }

    public void removeAd(Ad ad) {
        adRepository.delete(ad);
    }

    public AdsDTO getAllAds() {
        List<Ad> ads = adRepository.findAll();
        ArrayList<AdDTO> adDTOs = ads.stream()
                .map(ad -> AdDTO.toDto(ad))
                .collect(Collectors.toCollection(() -> new ArrayList<>()));
        return new AdsDTO(adDTOs.size(), adDTOs);
    }

    public void removeAd(long id, Authentication authentication) {
        Ad ad = getAd(id);
        User user = ad.getUser();
        Permission.checkPermission(user, authentication);
        removeAd(ad);
    }

    public AdDTO updateAd(long id, AdUpdaterDTO adUpdaterDTO, Authentication authentication) {
        Ad ad = getAd(id);
        Permission.checkPermission(ad.getUser(), authentication);
        ad.setPrice(adUpdaterDTO.price);
        ad.setTitle(adUpdaterDTO.title);
        ad.setDescription(adUpdaterDTO.description);
        adRepository.save(ad);
        return AdDTO.toDto(ad);
    }

    public ImageDTO updateAdImage(long id, MultipartFile file, Authentication authentication) {
        Ad ad = getAd(id);
        Permission.checkPermission(ad.getUser(), authentication);
        fileService.removeImage(ad.getImagePath());
        ImageDTO imageDTO = fileService.writeImage(file, Paths.IMAGE_DIRECTORY);
        ad.setImagePath(imageDTO.path);
        saveAd(ad);
        return imageDTO;
    }

    public ImageDTO getAdImage(long id) {
        Ad ad = getAd(id);
        return fileService.readImage(ad.getImagePath());
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
        commentService.saveComment(comment);
        adRepository.save(ad);
        return CommentDTO.toDto(comment);
    }

    public void removeComment(long id, long commentId, Authentication authentication) {
        Comment comment = commentService.getComment(commentId);
        Permission.checkPermission(comment.getAd().getUser(), authentication);
        commentService.removeComment(comment);
    }

    public CommentDTO updateComment(long id, long commentId, CommentUpdaterDTO commentUpdaterDTO, Authentication authentication) {
        Ad ad = getAd(id);
        Permission.checkPermission(ad.getUser(), authentication);
        Comment comment = commentService.getComment(commentId);
        comment.setText(commentUpdaterDTO.text);
        commentService.saveComment(comment);
        adRepository.save(ad);
        return CommentDTO.toDto(comment);
    }

    public AdDTO addAd(AdUpdaterDTO adUpdaterDTO, MultipartFile file, Authentication authentication) {
        User user = userService.getUser(authentication.getName());
        Ad ad = new Ad();
        ImageDTO imageDTO = fileService.writeImage(file, Paths.IMAGE_DIRECTORY);
        ad.setPrice(adUpdaterDTO.price);
        ad.setTitle(adUpdaterDTO.title);
        ad.setDescription(adUpdaterDTO.description);
        ad.setImagePath(imageDTO.path);
        user.addAd(ad);
        saveAd(ad);
        userService.saveUser(user);
        return AdDTO.toDto(ad);
    }


}
