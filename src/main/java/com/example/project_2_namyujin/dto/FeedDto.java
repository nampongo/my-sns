package com.example.project_2_namyujin.dto;

import com.example.project_2_namyujin.model.FeedEntity;
import com.example.project_2_namyujin.model.UserEntity;
import lombok.Data;

import java.util.List;

@Data
public class FeedDto {
    private Long id;
    private String title;
    private String content;

    private List<String> imageUrls;
    private UserEntity user;
    private boolean deleted;

    public static FeedDto fromEntity(FeedEntity entity) {
        FeedDto dto = new FeedDto();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setImageUrls(entity.getImageUrls());
        dto.setUser(entity.getUser());
        dto.setDeleted(entity.isDeleted());
        return dto;
    }

    @Data
    public static class upload {
        private String title;
        private String content;
    }

    @Data
    public static class paged {
        private String title;
        private String username;
        private String firstImage;

        public static FeedDto.paged fromEntity(FeedEntity entity) {
            FeedDto.paged paged = new FeedDto.paged();
            paged.setTitle(entity.getTitle());
            paged.setUsername(entity.getUser().getUsername());
            if (entity.getImageUrls().isEmpty())
                paged.setFirstImage("src/main/resources/static/images/default.png");
            else paged.setFirstImage(entity.getImageUrls().get(0));

            return paged;
        }
    }
}
