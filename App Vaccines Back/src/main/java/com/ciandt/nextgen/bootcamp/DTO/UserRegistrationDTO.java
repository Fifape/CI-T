package com.ciandt.nextgen.bootcamp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDTO {
    private String name;
    private String email;
    private String username;
    private String password;
    private Long baseId;
    private Long pictureId;
}

