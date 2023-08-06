package com.example.project_2_namyujin.service;

import com.example.project_2_namyujin.Repository.FeedRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class FeedService {
    private final FeedRepository feedRepository;


}
