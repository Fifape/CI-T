package com.ciandt.nextgen.bootcamp.service;

import com.ciandt.nextgen.bootcamp.DTO.UserRegistrationDTO;
import com.ciandt.nextgen.bootcamp.exceptions.ForbiddenException;
import com.ciandt.nextgen.bootcamp.exceptions.NotFoundException;
import com.ciandt.nextgen.bootcamp.model.User;
import com.ciandt.nextgen.bootcamp.repository.UserBaseRepository;
import com.ciandt.nextgen.bootcamp.repository.UserPictureRepository;
import com.ciandt.nextgen.bootcamp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class UserRegistrationService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserBaseRepository userBaseRepository;
    private UserPictureRepository userPictureRepository;

    public UserRegistrationDTO createNewUser(String name, String email, String username, String password, Long baseId) {

        // Checks if username exists
        boolean usernameExists = userRepository.findByUsername(username).isPresent();
        if (usernameExists) {
            throw new ForbiddenException("Nome de usuário já existe");
        }

        // Checks if email exists
        boolean emailExists = userRepository.findByEmail(email).isPresent();
        if (emailExists) {
            throw new ForbiddenException("Endereço de email já existe");
        }

        // Checks if base id exists
        if (baseId <= 0 || baseId >= 5) {
            throw new ForbiddenException("Base não encontrada");
        }

        // Create Registration
        User userRegistration = new User(name, username,
                passwordEncoder.encode(password), email,
                userBaseRepository.findById(baseId)
                        .orElseThrow(() -> new IllegalArgumentException("Base não encontrada")),
                null);
        User savedUser = userRepository.save(userRegistration);

        return new UserRegistrationDTO(savedUser.getName(), savedUser.getEmail(), savedUser.getUsername(), null, savedUser.getBase().getId(), 1L);
    }

    public UserRegistrationDTO updateUser(Long id, String email, String username, Long baseId, Long pictureId) {
        // Search for User
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        boolean usernameExists = userRepository.findByUsername(username).isPresent();
        if (usernameExists && !Objects.equals(username, user.getUsername())) {
            throw new ForbiddenException("Nome de usuário já existe");
        }

        // Checks if email exists
        boolean emailExists = userRepository.findByEmail(email).isPresent();
        if (emailExists && !Objects.equals(email, user.getEmail())) {
            throw new ForbiddenException("Endereço de email já existe");
        }

        // Checks if base id exists
        if ((baseId <= 0 || baseId >= 5) && !baseId.equals(user.getBase().getId())) {
            throw new ForbiddenException("Base não encontrada");
        }

        // Update user
        user.setEmail(email);
        user.setUsername(username);
        user.setAdmin(false);
        user.setBase(userBaseRepository.findById(baseId)
                .orElseThrow(() -> new IllegalArgumentException("Base não encontrada")));
        user.setPicture(userPictureRepository.findById(pictureId)
                .orElseThrow(() -> new IllegalArgumentException("Foto não encontrada")));

        // Update in DB
        User updatedUser = userRepository.save(user);

        // Return User changes
        return new UserRegistrationDTO(updatedUser.getName(), updatedUser.getEmail(),updatedUser.getUsername(), null, updatedUser.getBase().getId(), updatedUser.getPicture().getId());
    }


    public String updateUserPassword(Long id, String password) {
        // Search for User
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        if (passwordEncoder.matches(password, user.getPassword())) {
            throw new ForbiddenException("Coloca uma senha diferente à antiga");
        }

        // Update user
        user.setPassword(passwordEncoder.encode(password));

        // Update in DB
        User updatedUser = userRepository.save(user);

        // Return User changes
        return "Password Updated";
    }
}