package com.example.project_2_namyujin.dto;

import com.example.project_2_namyujin.model.FeedEntity;
import com.example.project_2_namyujin.model.UserEntity;
import com.example.project_2_namyujin.model.CommentEntity;
import lombok.Data;

@Data
public class CommentDto {
    private Long id;
    private String content;
    private boolean deleted;

    private UserEntity user;
    private FeedEntity feed;

    public static CommentDto fromEntity(CommentEntity entity) {
        CommentDto dto = new CommentDto();
        dto.setId(entity.getId());
        dto.setContent(entity.getContent());
        dto.setUser(entity.getUser());
        dto.setFeed(entity.getFeed());
        return dto;
    }

    @Data
    public static class create {
        private String content;
        private Long feedId;
    }
}
