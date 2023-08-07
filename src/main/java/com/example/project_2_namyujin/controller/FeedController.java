package com.example.project_2_namyujin.controller;

import com.example.project_2_namyujin.dto.FeedDto;
import com.example.project_2_namyujin.dto.UserDto;
import com.example.project_2_namyujin.service.FeedService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("feeds")
@AllArgsConstructor
public class FeedController {
    private final FeedService service;

    // 피드 작성
    @PostMapping
    public void uploadText(
            @RequestBody FeedDto.upload feed,
            @AuthenticationPrincipal UserDto user
    ) {
        service.uploadFeedText(feed, user);
    }

    // (기존)피드에 이미지 추가
    @PutMapping("{feedId}/image")
    public void addImage(
            @PathVariable("feedId") Long id,
            @RequestParam("file") MultipartFile file,
            @AuthenticationPrincipal UserDto user
    ) throws Exception {
        service.uploadFeedImg(id, file, user);
    }

    // 특정 사용자의 전체 피드 조회
    @GetMapping("/user/{userId}")
    public Page<FeedDto.paged> readAll(
            @PathVariable("userId") Long userId,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "limit", defaultValue = "20") Integer limit
    ) {
        return service.readFeedPaged(userId, page, limit);
    }

    // 특정 피드 조회
    @GetMapping("/{feedId}")
    public FeedDto read(
            @PathVariable("feedId") Long feedId,
            @AuthenticationPrincipal UserDto user
    ) throws Exception {
        return service.readFeed(feedId, user);
    }

    // 피드의 특정 이미지 삭제
    @DeleteMapping("/{feedId}/image")
    public void deleteImage(
            @PathVariable("feedId") Long feedId,
            @RequestParam("idx") int imageIdx,
            @AuthenticationPrincipal UserDto dto
    ) throws Exception {
        service.deleteFeedImage(feedId, imageIdx, dto);
    }

    // 피드 삭제
    @DeleteMapping("/{feedId}")
    public void delete(
            @PathVariable("feedId") Long feedId,
            @AuthenticationPrincipal UserDto dto
    ) throws Exception {
        service.deleteFeed(feedId, dto);
    }
}
