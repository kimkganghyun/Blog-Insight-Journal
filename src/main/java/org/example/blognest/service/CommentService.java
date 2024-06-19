package org.example.blognest.service;

import lombok.RequiredArgsConstructor;
import org.example.blognest.model.Comment;
import org.example.blognest.model.Post;
import org.example.blognest.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    // 새로운 댓글 작성
    public Comment create(Comment comment) {
        comment.setCreatedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    // 댓글 수정
    public Comment updateComment(Comment comment) {
        comment.setUpdatedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    // 댓글 ID로 댓글 찾기
    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    // 포스트 댓글 목록 가져오기
    public List<Comment> getCommentsByPost(Post post) {
        return commentRepository.findByPost(post);
    }

    // 부모 댓글 -> 대댓글 목록 가져오기
    public List<Comment> getRepliesByParent(Comment parent) {
        return commentRepository.findByParent(parent);
    }

    // 댓글 삭제
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
