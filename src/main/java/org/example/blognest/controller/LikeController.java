package org.example.blognest.controller;

import lombok.RequiredArgsConstructor;
import org.example.blognest.model.Like;
import org.example.blognest.model.Post;
import org.example.blognest.model.User;
import org.example.blognest.service.LikeService;
import org.example.blognest.service.PostService;
import org.example.blognest.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikeController {

    private final LikeService likeService;
    private final PostService postService;
    private final UserService userService;

    // 새로운 좋아요 추가
    @PostMapping("/add")
    public ResponseEntity<Like> addLike(@RequestParam String username, @RequestParam Long postId) {
        Optional<User> user = userService.findByUsername(username);
        Optional<Post> post = postService.getPostById(postId);
        if (user.isPresent() && post.isPresent()) {
            Like like = new Like();
            like.setUser(user.get());
            like.setPost(post.get());
            Like createdLike = likeService.addLike(like);
            return ResponseEntity.ok(createdLike);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    // 좋아요 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteLike(@PathVariable Long id) {
        likeService.deleteLike(id);
        return ResponseEntity.noContent().build();
    }

    // 특정 포스트에 대한 좋아요 목록 조회
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Like>> getLikesByPost(@PathVariable Long postId) {
        Optional<Post> post = postService.getPostById(postId);
        if (post.isPresent()) {
            List<Like> likes = likeService.getLikesByPost(post.get());
            return ResponseEntity.ok(likes);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    // 특정 사용자의 좋아요 목록 조회
    @GetMapping("/user/{username}")
    public ResponseEntity<List<Like>> getLikesByUser(@PathVariable String username) {
        Optional<User> user = userService.findByUsername(username);
        if (user.isPresent()) {
            List<Like> likes = likeService.getLikesByUser(user.get());
            return ResponseEntity.ok(likes);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }
}
