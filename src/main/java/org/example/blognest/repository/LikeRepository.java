package org.example.blognest.repository;

import org.example.blognest.model.Like;
import org.example.blognest.model.Post;
import org.example.blognest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long>{

    // 사용자 좋아요 목록
    List<Like> findByUser(User user);

    // 포스트 좋아요 목록
    List<Like> findByPost(Post post);
}
