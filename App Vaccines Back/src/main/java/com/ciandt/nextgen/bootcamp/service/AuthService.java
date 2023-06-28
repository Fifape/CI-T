package com.ciandt.nextgen.bootcamp.service;

import com.ciandt.nextgen.bootcamp.DTO.AuthDTO;
import com.ciandt.nextgen.bootcamp.DTO.AuthForm;
import com.ciandt.nextgen.bootcamp.exceptions.ForbiddenException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@AllArgsConstructor
public class AuthService {

    private final AuthenticationProvider authenticationProvider;

    public AuthDTO authenticate(AuthForm authForm) {
        try {
            authenticationProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authForm.getUsername(),
                            authForm.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new ForbiddenException("Credenciais inválidas");
        }

        String payload = authForm.getUsername() + ":" + authForm.getPassword();
        String token = Base64.getUrlEncoder().encodeToString(payload.getBytes());
        return new AuthDTO("Basic", token);
    }
}
