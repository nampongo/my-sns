package com.example.project_2_namyujin.controller;

import com.example.project_2_namyujin.dto.UserDto;
import com.example.project_2_namyujin.service.FeedService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("feeds")
@AllArgsConstructor
public class FeedController {
    private final FeedService service;

    // 피드 작성
    @PostMapping
    public void upload(
            @AuthenticationPrincipal UserDto dto
    ) {

    }
}
