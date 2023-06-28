package com.ciandt.nextgen.bootcamp.DTO;

import com.ciandt.nextgen.bootcamp.model.UserPicture;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String name;
    private String username;
    private String email;
    private Boolean admin;
    private String base;
    @ManyToOne
    @JoinColumn(name = "picture_id")
    private UserPicture picture;
}