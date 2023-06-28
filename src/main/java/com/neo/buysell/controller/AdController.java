package com.neo.buysell.controller;

import com.neo.buysell.model.dto.TAd;
import com.neo.buysell.model.dto.TAds;
import com.neo.buysell.model.dto.TComment;
import com.neo.buysell.model.dto.TComments;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(value = {"http://localhost:3000", "http://127.0.0.1:3000"})
@RestController
@RequestMapping("/ads")
public class AdController {

    @GetMapping()
    public TAds getAllAds() {
        //logic
        return new TAds();
    }

    @PostMapping()
    public TAd addAd(@RequestParam MultipartFile file) {
        //logic
        return new TAd();
    }

    @GetMapping("/{id}")
    public TAd getAd(@PathVariable("id") long id) {
        //logic
        return new TAd();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteAd(@PathVariable("id") long id) {
        //logic
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}")
    public TAd updateAd(@PathVariable("id") long id, @RequestBody TAd tAd) {
        //logic
        return tAd;
    }

    @GetMapping("/me")
    public TAds getAllAds(HttpServletRequest request) {
        //logic
        return new TAds();
    }

    @PatchMapping("/{id}/image")
    public ResponseEntity updateAdImage(@PathVariable("id") long id, @RequestParam MultipartFile file) {
        //logic
        return ResponseEntity.ok("SOME STRING");
    }

    @GetMapping("/{id}/comments")
    public TComments getAdComments(@PathVariable("id") long id) {
        //logic
        return new TComments();
    }

    @PostMapping("/{id}/comments")
    public TComment addComment(@PathVariable("id") long id, @RequestBody String text) {
        //logic
        return new TComment();
    }

    @DeleteMapping("/{id}/comments/{commentId}")
    public ResponseEntity deleteComment(@PathVariable("id") long id, @PathVariable("commentId") long commentId) {
        //logic
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/comments/{commentId}")
    public TComment updateComment(@PathVariable("id") long id, @PathVariable("commentId") long commentId, @RequestBody String text) {
        //logic
        return new TComment();
    }


}
