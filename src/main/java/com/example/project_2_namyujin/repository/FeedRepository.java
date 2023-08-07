package com.example.project_2_namyujin.repository;

import com.example.project_2_namyujin.model.FeedEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<FeedEntity, Long> {
    Page<FeedEntity> findAllByUserIdAndAndDeletedIsFalse(Long userId, Pageable pageable);

}
