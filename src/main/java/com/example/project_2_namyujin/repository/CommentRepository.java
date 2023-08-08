package com.example.project_2_namyujin.repository;

import com.example.project_2_namyujin.model.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByFeedIdAndDeletedIsFalse(Long feedId);
    Optional<CommentEntity> findById(Long CommentId);

    List<CommentEntity> findAllByFeedId(Long feedId);
}
