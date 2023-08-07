package com.example.project_2_namyujin.service;

import com.example.project_2_namyujin.repository.UserRepository;
import com.example.project_2_namyujin.dto.UserDto;
import com.example.project_2_namyujin.model.UserEntity;
import com.example.project_2_namyujin.security.JwtRequestDto;
import com.example.project_2_namyujin.security.JwtTokenDto;
import com.example.project_2_namyujin.security.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Slf4j
@Service
public class UserService implements UserDetailsManager {
    private final UserRepository userRepository;
    private final JwtTokenUtils jwtTokenUtils;
    private final PasswordEncoder passwordEncoder;

    public UserService(
            UserRepository userRepository,
            JwtTokenUtils jwtTokenUtils, PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.jwtTokenUtils = jwtTokenUtils;
        this.passwordEncoder = passwordEncoder;
        createUser(UserDto.builder()
                .username("user1")
                .password(passwordEncoder.encode("1234"))
                .phone("010-0000-0000")
                .email("user1@gmail.com")
                .build()
        );
    }

    public JwtTokenDto loginUser(JwtRequestDto dto) {
        UserDetails userDetails = this.loadUserByUsername(dto.getUsername());

        if (!passwordEncoder.matches(dto.getPassword(), userDetails.getPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "비밀번호 불일치");

        JwtTokenDto response = new JwtTokenDto();
        response.setToken(jwtTokenUtils.generateToken(userDetails));
        return response;
    }

    @Override
    public void createUser(UserDetails user) {
        if (this.userExists(user.getUsername()))
            throw new ResponseStatusException(HttpStatus.CONFLICT);

        try {
            this.userRepository.save(((UserDto) user).newEntity());
        } catch (ClassCastException e) {
            log.error("failed to cast to {}", UserDto.class);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean userExists(String username) {
        return this.userRepository.existsByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty())
            throw new UsernameNotFoundException(username);
        return UserDto.fromEntity(optionalUser.get());
    }

    public void ensureImgFolderandUploadImg(String folderPath, MultipartFile file) {
        File userImgFolder = new File(folderPath);
        if (!userImgFolder.exists()) {
            try {
                userImgFolder.mkdir();
            } catch (Exception e) { e.getStackTrace(); }
        }

        try {
            Files.write(Paths.get(folderPath + "/" + file.getOriginalFilename()), file.getBytes());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage());
        }
    }

    public void updateUserImg(MultipartFile file, UserDto user) throws IOException {
        UserEntity userEntity = userRepository.findByUsername(user.getUsername()).get();

        // (유저의 프로필 이미지) 파일 위치 설정 : resources/static/images/{userId}/
        String folderPath = "src/main/resources/static/images/" + userEntity.getId();
        ensureImgFolderandUploadImg(folderPath, file);

        // 유저의 프로필 사진이 이미 있을 경우, 이전 사진 삭제
        if (userEntity.getImageUrl() != null) {
            new File(userEntity.getImageUrl()).delete();
            log.info("User Id {} : 기존 프로필 사진 삭제", userEntity.getUsername());
        }

        userEntity.setImageUrl(folderPath + "/" + file.getOriginalFilename());
        this.userRepository.save(userEntity);
        log.info("User Id {} : 프로필 사진이 업로드 되었습니다.", userEntity.getUsername());
    }

    @Override
    public void updateUser(UserDetails user) {}

    @Override
    public void deleteUser(String username) {}

    @Override
    public void changePassword(String oldPassword, String newPassword) {}
}
