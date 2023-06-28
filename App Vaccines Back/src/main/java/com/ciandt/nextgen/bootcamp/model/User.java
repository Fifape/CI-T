package com.ciandt.nextgen.bootcamp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "USERS")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String username;
    @JsonIgnore
    private String password;
    private String email;
    private Boolean admin;

    @ManyToOne
    @JoinColumn(name = "user_base_id")
    private UserBase base;

    @ManyToOne
    @JoinColumn(name = "picture_id")
    private UserPicture picture;

    public User(String name, String username, String password, String email, UserBase userBase, UserPicture userPicture) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
        this.admin = false;
        this.base = userBase;
        this.picture = userPicture != null ? userPicture : new UserPicture(1L);
    }
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

}
