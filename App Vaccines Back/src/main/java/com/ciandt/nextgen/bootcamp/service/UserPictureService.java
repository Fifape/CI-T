package com.ciandt.nextgen.bootcamp.service;

import com.ciandt.nextgen.bootcamp.exceptions.ForbiddenException;
import com.ciandt.nextgen.bootcamp.model.User;
import com.ciandt.nextgen.bootcamp.model.UserPicture;
import com.ciandt.nextgen.bootcamp.repository.UserPictureRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserPictureService {

    private static final String UPLOAD_DIR = "uploads";

    @Autowired
    private UserPictureRepository userPictureRepository;

    public void savePicture(MultipartFile file, Long userId) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        UserPicture picture = new UserPicture();
        picture.setName(fileName);
        picture.setExtension(FilenameUtils.getExtension(fileName));
        picture.setPosted(userId);
        userPictureRepository.save(picture);

        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(picture.getId() + "." + picture.getExtension());
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public List<UserPicture> getPicturesByUserId(Long userId, User authenticatedUser) {

        // Checks if Logged User is the Posted Picture User
        if (!authenticatedUser.getId().equals(userId)) {
            throw new ForbiddenException("Usuário não autorizado a visualizar as fotos.");
        }

        List<UserPicture> pictures = new ArrayList<>();

        if (!authenticatedUser.getId().equals(1L)){
            pictures.addAll(userPictureRepository.findByPosted(1L));
        }

        pictures.addAll(userPictureRepository.findByPosted(userId));

        return pictures;
    }
}
