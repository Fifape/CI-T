package com.ciandt.nextgen.bootcamp.controller;

import com.ciandt.nextgen.bootcamp.model.UserBase;
import com.ciandt.nextgen.bootcamp.repository.UserBaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/base")
public class UserBaseController {

    private final UserBaseRepository userBaseRepository;

    @Autowired
    public UserBaseController(UserBaseRepository userBaseRepository) {
        this.userBaseRepository = userBaseRepository;
    }

    @GetMapping("/all")
    public List<UserBase> getAllUserBases() {
        return userBaseRepository.findAll();
    }
}

