package org.example.blognest.service;

import lombok.RequiredArgsConstructor;
import org.example.blognest.model.Like;
import org.example.blognest.model.Post;
import org.example.blognest.model.User;
import org.example.blognest.repository.LikeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;

    // 새로운 좋아요 추가
    public Like addLike(Like like) {
        return likeRepository.save(like);
    }

    // 좋아요 삭제
    public void deleteLike(Long id) {
        likeRepository.deleteById(id);
    }

    // 포스트 좋아요 목록 가져오기
    public List<Like> getLikesByPost(Post post) {
        return likeRepository.findByPost(post);
    }

    // 사용자 좋아요 목록 가져오기
    public List<Like> getLikesByUser(User user) {
        return likeRepository.findByUser(user);
    }
}
