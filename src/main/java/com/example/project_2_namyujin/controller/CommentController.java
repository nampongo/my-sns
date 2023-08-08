package com.example.project_2_namyujin.controller;

import com.example.project_2_namyujin.dto.CommentDto;
import com.example.project_2_namyujin.dto.UserDto;
import com.example.project_2_namyujin.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("comments")
@AllArgsConstructor
public class CommentController {
    private final CommentService service;

    @PostMapping
    public void create(
            @RequestBody CommentDto.create comment,
            @AuthenticationPrincipal UserDto user
    ) throws Exception {
        service.createComment(comment, user);
    }

    @PutMapping("/{commentId}")
    public void editContent(
            @PathVariable("commentId") Long id,
            @RequestBody String content,
            @AuthenticationPrincipal UserDto user
    ) throws Exception {
        service.editCommentContent(id, user, content);
    }

    @DeleteMapping("/{commentId}")
    public void delete(
            @PathVariable("commentId") Long id,
            @AuthenticationPrincipal UserDto dto
    ) throws Exception {
        service.deleteComment(id, dto);
    }
}
