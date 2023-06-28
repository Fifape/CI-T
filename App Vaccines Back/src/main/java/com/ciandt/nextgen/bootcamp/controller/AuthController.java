package com.ciandt.nextgen.bootcamp.controller;

import com.ciandt.nextgen.bootcamp.DTO.AuthDTO;
import com.ciandt.nextgen.bootcamp.DTO.AuthForm;
import com.ciandt.nextgen.bootcamp.exceptions.ForbiddenException;
import com.ciandt.nextgen.bootcamp.exceptions.NotFoundException;
import com.ciandt.nextgen.bootcamp.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthController {

    private final AuthService authService;
    @PostMapping
    public ResponseEntity<AuthDTO> authenticate(@RequestParam String username, @RequestParam String password){

        AuthForm form = new AuthForm();
        form.setUsername(username);
        form.setPassword(password);

        return ResponseEntity.ok().body(authService.authenticate(form));
    }

    @ExceptionHandler({ForbiddenException.class})
    public ResponseEntity<?> handleForbiddenException(ForbiddenException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ex.getMessage());
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<?> handleNotFoundException(NotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }
}
