package com.ciandt.nextgen.bootcamp.controller;

import com.ciandt.nextgen.bootcamp.model.User;
import com.ciandt.nextgen.bootcamp.model.UserPicture;
import com.ciandt.nextgen.bootcamp.service.UserPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/pictures")
public class UserPictureController {

    @Autowired
    private UserPictureService userPictureService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPicture(@RequestParam("file") MultipartFile file, @RequestParam("userId") Long userId) {
        try {
            userPictureService.savePicture(file, userId);
            return ResponseEntity.ok("Picture uploaded successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload picture.");
        }
    }

    @GetMapping("/list/{userId}")
    public ResponseEntity<List<UserPicture>> listPicturesByUserId(@PathVariable("userId") Long userId, Authentication authentication) {
        User authenticatedUser = (User) authentication.getPrincipal();
        List<UserPicture> pictures = userPictureService.getPicturesByUserId(userId, authenticatedUser);
        return ResponseEntity.ok(pictures);
    }
}
