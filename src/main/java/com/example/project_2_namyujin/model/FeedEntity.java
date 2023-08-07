package com.example.project_2_namyujin.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "feeds")
@Data
public class FeedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String content;

    private List<String> imageUrls;
    private boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private UserEntity user;

    public List<String> addImageUrl(String url) {
        imageUrls.add(url);
        return imageUrls;
    }

}
