package com.neo.buysell.model.service;

import com.neo.buysell.model.dto.*;
import com.neo.buysell.model.entity.Ad;
import com.neo.buysell.model.entity.Comment;
import com.neo.buysell.repository.AdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    private final AdRepository adRepository;
    private final CommentService commentService;

    @Autowired
    public AdService(AdRepository adRepository, CommentService commentService) {
        this.adRepository = adRepository;
        this.commentService = commentService;
    }

    public AdsDTO getAllAds() {
        List<Ad> ads = adRepository.findAll();
        ArrayList<AdDTO> adDTOs = ads.stream()
                .map(ad -> AdDTO.toDto(ad))
                .collect(Collectors.toCollection(() -> new ArrayList<>()));
        return new AdsDTO(adDTOs.size(), adDTOs);
    }

    public ExtendedAdDTO getAd(long id) {
        Ad ad = adRepository.findById(id).orElseThrow(() -> new RuntimeException());//TODO
        return ExtendedAdDTO.toDto(ad);
    }

    public void removeAd(long id) {
        boolean isDeleted = adRepository.deleteAdById(id);
        if (!isDeleted) {
            throw new RuntimeException(); //TODO
        }
    }

    public AdDTO updateAd(long id, AdUpdaterDTO adUpdaterDTO) {
        Ad ad = adRepository.findById(id).orElseThrow(() -> new RuntimeException());//TODO
        ad.setPrice(adUpdaterDTO.price);
        ad.setTitle(adUpdaterDTO.title);
        ad.setDescription(adUpdaterDTO.description);
        adRepository.save(ad);
        return AdDTO.toDto(ad);
    }

    public AdDTO updateAd(long id, MultipartFile image) {
        Ad ad = adRepository.findById(id).orElseThrow(() -> new RuntimeException());//TODO
        try {
            UUID uuid = UUID.randomUUID();
            Path path = Path.of(IMAGE_PATH + uuid + image.getOriginalFilename());
            Files.createDirectory(Path.of(IMAGE_PATH));
            Files.write(path, image.getBytes());
            Files.delete(Path.of(ad.getImagePath()));
            ad.setImagePath(path.toString());
            adRepository.save(ad);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new AdDTO();
    }

    public CommentsDTO getAdComments(long id) {
        Ad ad = adRepository.findById(id).orElseThrow(() -> new RuntimeException());//TODO
        ArrayList<CommentDTO> commentDTOs = ad.getComments().stream()
                .map(comment -> CommentDTO.toDto(comment))
                .collect(Collectors.toCollection(() -> new ArrayList<>()));
        return new CommentsDTO(commentDTOs.size(), commentDTOs);
    }

    public CommentDTO addCommentToAd(long id, CommentUpdaterDTO commentUpdaterDTO) {
        Ad ad = adRepository.findById(id).orElseThrow(() -> new RuntimeException());//TODO
        Comment comment = new Comment();
        comment.setText(commentUpdaterDTO.text);
        comment.setCreationTime(System.currentTimeMillis());
        ad.addComment(comment);
        commentService.save(comment);
        adRepository.save(ad);
        return CommentDTO.toDto(comment);
    }


    public void removeComment(long id, long commentId) {
        Ad ad = adRepository.findById(id).orElseThrow(() -> new RuntimeException());//TODO
        Comment comment = commentService.getComment(commentId);
        ad.removeComment(comment);
        adRepository.save(ad);
    }

    public CommentDTO updateComment(long id, long commentId, CommentUpdaterDTO commentUpdaterDTO) {
        Ad ad = adRepository.findById(id).orElseThrow(() -> new RuntimeException());//TODO
        Comment comment = commentService.getComment(commentId);
        comment.setText(commentUpdaterDTO.text);
        commentService.save(comment);
        adRepository.save(ad);
        return CommentDTO.toDto(comment);
    }
}
