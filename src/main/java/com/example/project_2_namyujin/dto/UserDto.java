package com.example.project_2_namyujin.dto;

import com.example.project_2_namyujin.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String imageurl;

    @Data
    public class register {
        private String username;
        private String password;
        private String passwordCheck;
        private String email;
        private String phone;
    }

    public static UserDto fromEntity(UserEntity entity) {
        UserDto details = new UserDto();
        details.id = entity.getId();
        details.username = entity.getUsername();
        details.password = entity.getPassword();
        details.email = entity.getEmail();
        details.phone = entity.getPhone();
        details.imageurl = entity.getImageurl();
        return details;
    }

    public UserEntity newEntity() {
        UserEntity entity = new UserEntity();
        entity.setUsername(username);
        entity.setPassword(password);
        entity.setEmail(email);
        entity.setPhone(phone);
        entity.setImageurl(imageurl);
        return entity;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }


    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
