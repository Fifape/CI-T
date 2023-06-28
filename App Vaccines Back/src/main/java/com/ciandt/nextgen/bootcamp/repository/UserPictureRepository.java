package com.ciandt.nextgen.bootcamp.repository;

import com.ciandt.nextgen.bootcamp.model.UserPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPictureRepository extends JpaRepository<UserPicture, Long> {
    Optional<UserPicture> findById(Long id);
    List<UserPicture> findByPosted(Long userId);
}

