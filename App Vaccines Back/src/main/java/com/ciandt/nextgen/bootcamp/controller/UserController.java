package com.ciandt.nextgen.bootcamp.controller;

import com.ciandt.nextgen.bootcamp.DTO.UserDTO;
import com.ciandt.nextgen.bootcamp.DTO.mapper.UserMapper;
import com.ciandt.nextgen.bootcamp.model.User;
import com.ciandt.nextgen.bootcamp.repository.UserRepository;
import com.ciandt.nextgen.bootcamp.service.UserRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/user")
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private UserRegistrationService userDTOService;

    @GetMapping
    public ResponseEntity<UserDTO> getLoggedUser(@AuthenticationPrincipal User user){

        return ResponseEntity.ok().body(UserMapper.INSTANCE.modelToResponse(user));
    }

    @GetMapping("/test")
    public List<UserDTO> getALl(){

        return UserMapper.INSTANCE.modelsToResponses(userRepository.findAll());
    }

    @ExceptionHandler({UsernameNotFoundException.class})
    public ResponseEntity<?> handleUserNotFoundException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }
}
