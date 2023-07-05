package com.neo.buysell.controller;

import com.neo.buysell.model.dto.*;
import com.neo.buysell.model.service.AdService;
import com.neo.buysell.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(value = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping("/ads")
public class AdController {
    private final AdService adService;
    private final UserService userService;

    @Autowired
    public AdController(AdService adService, UserService userService) {
        this.adService = adService;
        this.userService = userService;
    }

    @GetMapping()
    public AdsDTO getAllAds() {
        AdsDTO allAds = adService.getAllAds();
        return allAds;
    }

    @PostMapping()
    public AdDTO addAd(@RequestParam("image") MultipartFile file) {
        //logic нужны данные авторизации
        return new AdDTO();
    }

    @GetMapping("/{id}")
    public ExtendedAdDTO getAd(@PathVariable("id") long id) {
        ExtendedAdDTO extendedAdDTO = adService.getAd(id);
        return extendedAdDTO;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAd(@PathVariable("id") long id) {
        adService.removeAd(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public AdDTO updateAd(@PathVariable("id") long id, @RequestBody AdUpdaterDTO adUpdaterDTO) {
        AdDTO adDTO = adService.updateAd(id, adUpdaterDTO);
        return adDTO;
    }

    @GetMapping("/me")
    public AdsDTO getAllUserAds() {
        //logic нужны данные авторизации
        return new AdsDTO();
    }

    @PatchMapping("/{id}/image")
    public ResponseEntity updateAdImage(@PathVariable("id") long id, @RequestParam("image") MultipartFile file) {
        adService.updateAd(id, file);
        return ResponseEntity.ok("SOME STRING"); // возвращать нужно octet stream, это что?
    }

    @GetMapping("/{id}/comments")
    public CommentsDTO getAdComments(@PathVariable("id") long id) {
        CommentsDTO commentsDTO = adService.getAdComments(id);
        return commentsDTO;
    }

    @PostMapping("/{id}/comments")
    public CommentDTO addComment(@PathVariable("id") long id, @RequestBody CommentUpdaterDTO commentUpdaterDTO) {
        CommentDTO commentDTO = adService.addCommentToAd(id, commentUpdaterDTO);
        return commentDTO;
    }

    @DeleteMapping("/{id}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") long id, @PathVariable("commentId") long commentId) {
        adService.removeComment(id, commentId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/comments/{commentId}")
    public CommentDTO updateComment(@PathVariable("id") long id, @PathVariable("commentId") long commentId, @RequestBody CommentUpdaterDTO commentUpdaterDTO) {
        CommentDTO commentDTO = adService.updateComment(id, commentId, commentUpdaterDTO);
        return commentDTO;
    }

}
