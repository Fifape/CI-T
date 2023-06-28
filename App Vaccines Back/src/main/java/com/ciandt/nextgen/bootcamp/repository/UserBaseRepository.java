package com.ciandt.nextgen.bootcamp.repository;

import com.ciandt.nextgen.bootcamp.model.UserBase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserBaseRepository extends JpaRepository<UserBase, Long> {

    Optional<UserBase> findById(Long id);

}

