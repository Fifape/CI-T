package com.ciandt.nextgen.bootcamp.controller;

import com.ciandt.nextgen.bootcamp.DTO.UserRegistrationDTO;
import com.ciandt.nextgen.bootcamp.repository.UserRepository;
import com.ciandt.nextgen.bootcamp.service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserRegistrationController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private UserRegistrationService userRegistrationService;

    @PostMapping("/register")
    public ResponseEntity<UserRegistrationDTO> registerNewUser(@RequestParam String name, String email, String username, String password, Long baseId) {

        UserRegistrationDTO createUserRegistrationDTO = userRegistrationService.createNewUser(name, email, username, password, baseId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createUserRegistrationDTO);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<UserRegistrationDTO> updateUser(@PathVariable Long id, @RequestParam String email, String username, Long baseId, Long pictureId) {
        UserRegistrationDTO updatedUser = userRegistrationService.updateUser(id, email, username, baseId, pictureId);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/edit/password/{id}")
    public ResponseEntity<String> updateUserPassword(@PathVariable Long id, @RequestParam String password) {
        String updatedUser = userRegistrationService.updateUserPassword(id, password);
        return ResponseEntity.ok(updatedUser);
    }
}