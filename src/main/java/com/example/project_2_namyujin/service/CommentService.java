package com.example.project_2_namyujin.service;

import com.example.project_2_namyujin.dto.CommentDto;
import com.example.project_2_namyujin.dto.UserDto;
import com.example.project_2_namyujin.model.CommentEntity;
import com.example.project_2_namyujin.model.FeedEntity;
import com.example.project_2_namyujin.model.UserEntity;
import com.example.project_2_namyujin.repository.CommentRepository;
import com.example.project_2_namyujin.repository.FeedRepository;
import com.example.project_2_namyujin.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class CommentService {
    private final FeedRepository feedRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public void createComment(CommentDto.create commentDto, UserDto userDto) throws Exception {
        CommentEntity comment = new CommentEntity();
        comment.setContent(commentDto.getContent());
        comment.setUser(loadUserEntityByUsername(userDto.getUsername()));
        comment.setFeed(loadFeedEntityById(commentDto.getFeedId()));
        commentRepository.save(comment);
        log.info("Comment Id {} : 새로운 댓글이 등록되었습니다.", comment.getId());
    }

    public void editCommentContent(Long commentId, UserDto userDto, String content) throws Exception {
        CommentEntity comment = loadCommentEntityById(commentId);
        if (!comment.getUser().getUsername().equals(userDto.getUsername()))
            throw new Exception("Comment id {} : 해당 댓글 수정에 대한 권한이 없습니다. ");

        comment.setContent(content);
        commentRepository.save(comment);
        log.info("Comment Id {} : 댓글 내용이 수정되었습니다.", commentId);
    }

    public void deleteComment(Long commentId, UserDto userDto) throws Exception {
        CommentEntity comment = loadCommentEntityById(commentId);
        if (!comment.getUser().getUsername().equals(userDto.getUsername()))
            throw new Exception("Comment id {} : 해당 댓글 삭제에 대한 권한이 없습니다.");

        comment.setDeleted(true);
        commentRepository.save(comment);
        log.info("Comment Id {} : 댓글이 삭제 되었습니다.", commentId);
    }


    //-------------------------------------------------------------------------------

    public FeedEntity loadFeedEntityById(Long feedId) throws Exception {
        try {
            FeedEntity entity = feedRepository.findById(feedId).get();
            return entity;
        } catch (Exception e) { throw new Exception(e.getMessage()); }
    }

    public UserEntity loadUserEntityByUsername(String username) throws Exception {
        try {
            UserEntity entity = userRepository.findByUsername(username).get();
            return entity;
        } catch (Exception e) { throw new Exception(e.getMessage()); }
    }

    public CommentEntity loadCommentEntityById(Long id) throws Exception {
        try {
            CommentEntity entity = commentRepository.findById(id).get();
            return entity;
        } catch (Exception e) { throw new Exception(e.getMessage()); }
    }

}
