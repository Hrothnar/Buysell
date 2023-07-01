package com.neo.buysell.controller;

import com.neo.buysell.model.dto.AdDTO;
import com.neo.buysell.model.dto.AdsDTO;
import com.neo.buysell.model.dto.CommentDTO;
import com.neo.buysell.model.dto.CommentsDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(value = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping("/ads")
public class AdController {

    @GetMapping()
    public AdsDTO getAllAds() {
        //logic
        return new AdsDTO();
    }

    @PostMapping()
    public AdDTO addAd(@RequestParam MultipartFile file) {
        //logic
        return new AdDTO();
    }

    @GetMapping("/{id}")
    public AdDTO getAd(@PathVariable("id") long id) {
        //logic
        return new AdDTO();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAd(@PathVariable("id") long id) {
        //logic
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public AdDTO updateAd(@PathVariable("id") long id, @RequestBody AdDTO adDTO) {
        //logic
        return adDTO;
    }

    @GetMapping("/me")
    public AdsDTO getAllAds(HttpServletRequest request) {
        //logic
        return new AdsDTO();
    }

    @PatchMapping("/{id}/image")
    public ResponseEntity updateAdImage(@PathVariable("id") long id, @RequestParam MultipartFile file) {
        //logic
        return ResponseEntity.ok("SOME STRING");
    }

    @GetMapping("/{id}/comments")
    public CommentsDTO getAdComments(@PathVariable("id") long id) {
        //logic
        return new CommentsDTO();
    }

    @PostMapping("/{id}/comments")
    public CommentDTO addComment(@PathVariable("id") long id, @RequestBody String text) {
        //logic
        return new CommentDTO();
    }

    @DeleteMapping("/{id}/comments/{commentId}")
    public ResponseEntity deleteComment(@PathVariable("id") long id, @PathVariable("commentId") long commentId) {
        //logic
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/comments/{commentId}")
    public CommentDTO updateComment(@PathVariable("id") long id, @PathVariable("commentId") long commentId, @RequestBody String text) {
        //logic
        return new CommentDTO();
    }


}
