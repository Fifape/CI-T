package com.ciandt.nextgen.bootcamp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PICTURE")
public class UserPicture {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String extension;
    private Long posted;

    public UserPicture(Long id) {
        this.id = id;
    }
}
