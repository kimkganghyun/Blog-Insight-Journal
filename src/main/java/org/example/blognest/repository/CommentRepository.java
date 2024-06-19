package org.example.blognest.repository;

import org.example.blognest.model.Comment;
import org.example.blognest.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 포스트 댓글 목록 찾기
    List<Comment> findByPost(Post post);

    // 부모 댓글 -> 대댓글 목록 찾기
    List<Comment> findByParent(Comment parent);
}
