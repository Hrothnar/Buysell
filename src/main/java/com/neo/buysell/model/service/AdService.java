package com.neo.buysell.model.service;

import com.neo.buysell.model.dto.*;
import com.neo.buysell.model.entity.Ad;
import com.neo.buysell.model.entity.Comment;
import com.neo.buysell.model.entity.User;
import com.neo.buysell.model.enumeration.RoleType;
import com.neo.buysell.model.exception.particular.EntityNotFound;
import com.neo.buysell.model.exception.particular.NotAuthorizedException;
import com.neo.buysell.model.exception.particular.RuntimeIOException;
import com.neo.buysell.model.repository.AdRepository;
import com.neo.buysell.model.util.Paths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AdService {

    private final AdRepository adRepository;
    private final CommentService commentService;
    private final UserService userService;

    @Autowired
    public AdService(AdRepository adRepository, CommentService commentService, UserService userService) {
        this.adRepository = adRepository;
        this.commentService = commentService;
        this.userService = userService;
    }

    public Ad getAd(long id) {
        return adRepository.findById(id).orElseThrow(() -> new EntityNotFound(Ad.class, HttpStatus.NOT_FOUND));
    }

    public void saveAd(Ad ad) {
        adRepository.save(ad);
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

    public void removeAd(long id, Authentication authentication) {
        Ad ad = getAd(id);
        User user = ad.getUser();
        if (isOwner(user, authentication) || isAdmin(authentication)) {
            adRepository.delete(ad);
        } else {
            throw new NotAuthorizedException(HttpStatus.FORBIDDEN);
        }
    }

    public AdDTO updateAd(long id, AdUpdaterDTO adUpdaterDTO, Authentication authentication) {
        Ad ad = getAd(id);
        if (isOwner(ad.getUser(), authentication) || isAdmin(authentication)) {
            ad.setPrice(adUpdaterDTO.price);
            ad.setTitle(adUpdaterDTO.title);
            ad.setDescription(adUpdaterDTO.description);
            adRepository.save(ad);
            return AdDTO.toDto(ad);
        }
        throw new NotAuthorizedException(HttpStatus.FORBIDDEN);
    }

    public ImageDTO updateAdImage(long id, MultipartFile file, Authentication authentication) {
        Ad ad = getAd(id);
        if (isOwner(ad.getUser(), authentication) || isAdmin(authentication)) {
            ImageDTO imageDTO;
            Path path = Path.of(ad.getImagePath());
            try {
                if (Files.exists(path)) {
                    Files.delete(path);
                }
                imageDTO = setAdImage(ad, file);
                adRepository.save(ad);
            } catch (IOException e) {
                throw new RuntimeIOException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return imageDTO;
        }
        throw new NotAuthorizedException(HttpStatus.FORBIDDEN);
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

    public void removeComment(long id, long commentId, Authentication authentication) {
        Comment comment = commentService.getComment(commentId);
        User user = comment.getAd().getUser();
        if (isOwner(user, authentication) || isAdmin(authentication)) {
            commentService.remove(comment);
        } else {
            throw new NotAuthorizedException(HttpStatus.FORBIDDEN);
        }
    }

    public CommentDTO updateComment(long id, long commentId, CommentUpdaterDTO commentUpdaterDTO, Authentication authentication) {
        Ad ad = getAd(id);
        if (isOwner(ad.getUser(), authentication) || isAdmin(authentication)) {
            Comment comment = commentService.getComment(commentId);
            comment.setText(commentUpdaterDTO.text);
            commentService.save(comment);
            adRepository.save(ad);
            return CommentDTO.toDto(comment);
        }
        throw new NotAuthorizedException(HttpStatus.FORBIDDEN);
    }

    public AdDTO addAd(AdUpdaterDTO adUpdaterDTO, MultipartFile file, Authentication authentication) {
        User user = userService.getUser(authentication.getName());
        Ad ad = new Ad();
        ad.setPrice(adUpdaterDTO.price);
        ad.setTitle(adUpdaterDTO.title);
        ad.setDescription(adUpdaterDTO.description);
        setAdImage(ad, file);
        user.addAd(ad);
        saveAd(ad);
        userService.save(user);
        return AdDTO.toDto(ad);
    }

    private ImageDTO setAdImage(Ad ad, MultipartFile file) {
        byte[] bytes;
        Path directory = Path.of(Paths.IMAGE_DIRECTORY);
        String originalFilename = file.getOriginalFilename();
        try {
            if (!Files.exists(directory)) {
                Files.createDirectory(directory);
            }
            String uuid = UUID.randomUUID().toString().substring(0, 4);
            bytes = file.getBytes();
            Path path = Path.of(Paths.IMAGE_DIRECTORY + uuid + originalFilename);
            Files.write(path, bytes);
            ad.setImagePath(path.toAbsolutePath().toString());
        } catch (IOException e) {
            throw new RuntimeIOException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        MediaType mediaType = ImageDTO.toMediatype(originalFilename);
        return new ImageDTO(bytes, mediaType);
    }

    public ImageDTO getImageDTO(long id) {
        Ad ad = getAd(id);
        String imagePath = ad.getImagePath();
        File file = new File(imagePath);
        byte[] bytes;
        try (FileInputStream fis = new FileInputStream(file)) {
            bytes = fis.readAllBytes();
        } catch (IOException e) {
            throw new RuntimeIOException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        MediaType mediaType = ImageDTO.toMediatype(imagePath);
        return new ImageDTO(bytes, mediaType);
    }

    private boolean isAdmin(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean isAdmin = false;
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals(RoleType.ADMIN.getRole())) {
                isAdmin = true;
                break;
            }
        }
        return isAdmin;
    }

    private boolean isOwner(User user, Authentication authentication) {
        return user.getUsername().equals(authentication.getName());
    }

}
