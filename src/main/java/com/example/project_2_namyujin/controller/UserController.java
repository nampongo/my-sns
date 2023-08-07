package com.example.project_2_namyujin.controller;

import com.example.project_2_namyujin.dto.UserDto;
import com.example.project_2_namyujin.security.JwtRequestDto;
import com.example.project_2_namyujin.security.JwtTokenDto;
import com.example.project_2_namyujin.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("users")
@AllArgsConstructor
public class UserController {
    private final PasswordEncoder passwordEncoder;
    private final UserService service;

    // 로그인
    @PostMapping("/login")
    public JwtTokenDto login(@RequestBody JwtRequestDto dto) {
        return service.loginUser(dto);
    }

    // 회원가입
    @PostMapping("/register")
    public void register(@RequestBody UserDto.register dto) throws Exception {
        if (dto.getPassword().equals(dto.getPasswordCheck())) {
            service.createUser(UserDto.builder()
                    .username(dto.getUsername())
                    .password(passwordEncoder.encode(dto.getPassword()))
                    .email(dto.getEmail())
                    .phone(dto.getPhone())
                    .build()
            );
            log.info("{} 회원가입이 완료되었습니다.", dto.getUsername());
        } else throw new Exception("회원가입 실패 : 비밀번호 불일치");
    }

    // 유저 프로필 사진 등록
    @PutMapping("/image")
    public void uploadImage(
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal UserDto user
    ) throws IOException {
        service.updateUserImg(file, user);
    }
}
