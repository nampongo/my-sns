package com.example.project_2_namyujin.dto;

import com.example.project_2_namyujin.model.FeedEntity;
import com.example.project_2_namyujin.model.UserEntity;
import lombok.Data;

import java.util.List;

@Data
public class FeedDto {
    private Long id;
    private Long writerId;
    private String title;
    private String content;

    private List<String> imageUrls;
    private UserEntity user;

    public static FeedDto fromEntity(FeedEntity entity) {
        FeedDto dto = new FeedDto();
        dto.setId(entity.getId());
        dto.setWriterId(entity.getWriterId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setImageUrls(entity.getImageUrls());
        dto.setUser(entity.getUser());
        return dto;
    }
}
