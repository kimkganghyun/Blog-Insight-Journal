package org.example.blognest.service;

import lombok.RequiredArgsConstructor;
import org.example.blognest.model.Post;
import org.example.blognest.model.User;
import org.example.blognest.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    // 새로운 포스트 작성
    public Post create(Post post) {
        post.setCreatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    // 포스트 수정
    public Post updatePost(Post post) {
        post.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    // 포스트 삭제
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    // 포스트 ID로 포스트 찾기
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }


    // 작성자 포스트 목록 가져오기
    public List<Post> getPostsByAuthor(User author) {
        return postRepository.findByAuthor(author);
    }

    // 모든 포스터 가져오기
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

}
