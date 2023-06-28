package com.ciandt.nextgen.bootcamp.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthForm {
    private String username;
    private String password;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VaccineDTO {
        private Long id;
        private String name;
        private String description;
    }
}
