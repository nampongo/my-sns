package com.example.project_2_namyujin.security;

import lombok.Data;

@Data
public class JwtRequestDto {
    private String username;
    private String password;
}
