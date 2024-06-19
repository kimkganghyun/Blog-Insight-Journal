package org.example.blognest.repository;

import org.example.blognest.model.Post;
import org.example.blognest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    // 작성자 포스트 목록 찾기
    List<Post> findByAuthor(User author);

    // 제목을 포함한 포스트 목록 찾기
    List<Post> findByTitleContaining(String title);
}
