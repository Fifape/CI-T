package com.ciandt.nextgen.bootcamp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthDTO {
    private String type;
    private String token;
}
