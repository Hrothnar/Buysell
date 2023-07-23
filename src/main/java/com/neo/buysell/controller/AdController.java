package com.neo.buysell.controller;

import com.neo.buysell.model.dto.*;
import com.neo.buysell.model.service.AdService;
import com.neo.buysell.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdDTO> addAd(@RequestPart("properties") AdUpdaterDTO adUpdaterDTO,
                                       @RequestPart("image") MultipartFile file,
                                       Authentication authentication) {
//        if (!authentication.isAuthenticated()) {
//            throw new NotAuthenticatedException(HttpStatus.UNAUTHORIZED);
//        }
        AdDTO adDTO = adService.addAd(adUpdaterDTO, file, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(adDTO);
    }

    @GetMapping("/{id}")
    public ExtendedAdDTO getAd(@PathVariable("id") long id, Authentication authentication) {
//        if (!authentication.isAuthenticated()) {
//            throw new NotAuthenticatedException(HttpStatus.UNAUTHORIZED);
//        }
        ExtendedAdDTO extendedAdDTO = adService.getAdDTO(id);
        return extendedAdDTO;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAd(@PathVariable("id") long id, Authentication authentication) {
//        if (!authentication.isAuthenticated()) {
//            throw new NotAuthenticatedException(HttpStatus.UNAUTHORIZED);
//        }
        adService.removeAd(id, authentication);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{id}")
    public AdDTO updateAd(@PathVariable("id") long id, @RequestBody AdUpdaterDTO adUpdaterDTO, Authentication authentication) {
//        if (!authentication.isAuthenticated()) {
//            throw new NotAuthenticatedException(HttpStatus.UNAUTHORIZED);
//        }
        AdDTO adDTO = adService.updateAd(id, adUpdaterDTO, authentication);
        return adDTO;
    }

    @GetMapping("/me")
    public AdsDTO getAllUserAds(Authentication authentication) {
//        if (!authentication.isAuthenticated()) {
//            throw new NotAuthenticatedException(HttpStatus.UNAUTHORIZED);
//        }
        AdsDTO userAds = userService.getUserAds(authentication);
        return userAds;
    }

    @PatchMapping("/{id}/image")
    public ResponseEntity<byte[]> updateAdImage(@PathVariable("id") long id,
                                                @RequestParam("image") MultipartFile file,
                                                Authentication authentication) {
//        if (!authentication.isAuthenticated()) {
//            throw new NotAuthenticatedException(HttpStatus.UNAUTHORIZED);
//        }
        ImageDTO imageDTO = adService.updateAdImage(id, file, authentication);
        byte[] bytes = imageDTO.bytes;
        return ResponseEntity.ok()
                .contentLength(bytes.length)
                .contentType(imageDTO.mediaType)
                .body(bytes);
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getAdImage(@PathVariable("id") long id, Authentication authentication) {
//        if (!authentication.isAuthenticated()) {
//            throw new NotAuthenticatedException(HttpStatus.UNAUTHORIZED);
//        }
        ImageDTO imageDTO = adService.getImageDTO(id);
        byte[] bytes = imageDTO.bytes;
        return ResponseEntity.ok()
                .contentLength(bytes.length)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(bytes);
    }

    @GetMapping("/{id}/comments")
    public CommentsDTO getAdComments(@PathVariable("id") long id, Authentication authentication) {
//        if (!authentication.isAuthenticated()) {
//            throw new NotAuthenticatedException(HttpStatus.UNAUTHORIZED);
//        }
        CommentsDTO commentsDTO = adService.getAdComments(id);
        return commentsDTO;
    }

    @PostMapping("/{id}/comments")
    public CommentDTO addComment(@PathVariable("id") long id, @RequestBody CommentUpdaterDTO commentUpdaterDTO, Authentication authentication) {
//        if (!authentication.isAuthenticated()) {
//            throw new NotAuthenticatedException(HttpStatus.UNAUTHORIZED);
//        }
        CommentDTO commentDTO = adService.addCommentToAd(id, commentUpdaterDTO);
        return commentDTO;
    }

    @DeleteMapping("/{id}/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") long id, @PathVariable("commentId") long commentId, Authentication authentication) {
//        if (!authentication.isAuthenticated()) {
//            throw new NotAuthenticatedException(HttpStatus.UNAUTHORIZED);
//        }
        adService.removeComment(id, commentId, authentication);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/comments/{commentId}")
    public CommentDTO updateComment(@PathVariable("id") long id,
                                    @PathVariable("commentId") long commentId,
                                    @RequestBody CommentUpdaterDTO commentUpdaterDTO,
                                    Authentication authentication) {
        CommentDTO commentDTO = adService.updateComment(id, commentId, commentUpdaterDTO, authentication);
        return commentDTO;
    }

}
