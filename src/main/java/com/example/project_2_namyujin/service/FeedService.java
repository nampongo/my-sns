package com.example.project_2_namyujin.service;

import com.example.project_2_namyujin.repository.FeedRepository;
import com.example.project_2_namyujin.repository.UserRepository;
import com.example.project_2_namyujin.dto.FeedDto;
import com.example.project_2_namyujin.dto.UserDto;
import com.example.project_2_namyujin.model.FeedEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class FeedService {
    private final FeedRepository feedRepository;
    private final UserRepository userRepository;

    public void uploadFeedText(FeedDto.upload feed, UserDto userDto) {
        FeedEntity feedEntity = new FeedEntity();
        feedEntity.setTitle(feed.getTitle());
        feedEntity.setContent(feed.getContent());
        feedEntity.setUser(userRepository.findByUsername(userDto.getUsername()).get());
        feedEntity.setImageUrls(new ArrayList<>());
        feedEntity.setDeleted(false);
        feedRepository.save(feedEntity);
    }

    public FeedEntity loadFeedEntitybyId(long feedId) throws Exception {
        if (!feedRepository.existsById(feedId) || feedRepository.findById(feedId).get().isDeleted())
            throw new Exception("Feed Id " + feedId + " : 존재하지 않는 피드 입니다.");
        return feedRepository.findById(feedId).get();
    }

    public void ensureImgFolderandUploadImg(String folderPath, MultipartFile file) {
        File userImgFolder = new File(folderPath);
        if (!userImgFolder.exists()) {
            try {
                userImgFolder.mkdir();
            } catch (Exception e) { e.getStackTrace(); }
        }

        try {
            Files.write(Paths.get(folderPath + "/" + file.getOriginalFilename()), file.getBytes());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage());
        }
    }

    public void uploadFeedImg(long feedId, MultipartFile file, UserDto userDto) throws Exception {
        FeedEntity feed = loadFeedEntitybyId(feedId);
        if (!feed.getUser().getUsername().equals(userDto.getUsername()))
            throw new Exception("Feed Id {} : 피드 작성자만 수정 가능합니다.");

        String folderPath = "src/main/resources/static/images/" + feed.getUser().getId();
        ensureImgFolderandUploadImg(folderPath, file);

        feed.addImageUrl(folderPath + "/" + file.getOriginalFilename());
        this.feedRepository.save(feed);
        log.info("Feed Id {} : 사진이 업로드 되었습니다.", feedId);
    }

    public Page<FeedDto.paged> readFeedPaged(Long userId, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by("id"));
        Page<FeedEntity> feedEntityPage = feedRepository.findAllByUserId(userId, pageable);
        return feedEntityPage.map(FeedDto.paged::fromEntity);
    }

    public FeedDto readFeed(Long feedId, UserDto userDto) throws Exception {
        return FeedDto.fromEntity(loadFeedEntitybyId(feedId));
    }

    public void deleteFeedImage(Long feedId, int imageIdx, UserDto user) throws Exception {
        FeedEntity entity = loadFeedEntitybyId(feedId);
        if (!entity.getUser().getUsername().equals(user.getUsername()))
            throw new Exception("해당 피드에 대한 수정 권한이 없습니다.");

        List<String> urls = entity.getImageUrls();
        String deleteIt = urls.remove(imageIdx);
        entity.setImageUrls(urls);
        feedRepository.save(entity);

        new File(deleteIt).delete();
        log.info("Feed Id {} : 피드의 이미지(idx={})가 삭제되었습니다.", feedId, imageIdx);
    }


    // TODO TEST
    public void deleteFeed(Long feedId, UserDto user) throws Exception {
        FeedEntity entity = loadFeedEntitybyId(feedId);
        if (!entity.getUser().getUsername().equals(user.getUsername()))
            throw new Exception("해당 피드에 대한 수정 권한이 없습니다.");

        entity.setDeleted(true);
        log.info("Feed Id {} : 해당 피드가 삭제되었습니다.", feedId);
    }
}
