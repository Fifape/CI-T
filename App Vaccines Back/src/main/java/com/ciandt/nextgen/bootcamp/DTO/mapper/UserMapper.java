package com.ciandt.nextgen.bootcamp.DTO.mapper;

import com.ciandt.nextgen.bootcamp.DTO.UserDTO;
import com.ciandt.nextgen.bootcamp.model.User;

import java.util.ArrayList;
import java.util.List;


public class UserMapper {
    public static UserMapper INSTANCE =  new UserMapper();

    private UserMapper(){}

    public UserDTO modelToResponse(User model){
        return new UserDTO(
                model.getId(),
                model.getName(),
                model.getUsername(),
                model.getEmail(),
                model.getAdmin(),
                model.getBase().getLocation(),
                model.getPicture()
        );
    }

    public List<UserDTO> modelsToResponses(List<User> user){
        List<UserDTO> lista = new ArrayList<>();
        for (User u:user){
            lista.add(modelToResponse(u));
        }
        return lista;
    }
}
