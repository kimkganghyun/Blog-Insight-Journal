package org.example.blognest.controller;

import lombok.RequiredArgsConstructor;
import org.example.blognest.model.Post;
import org.example.blognest.model.User;
import org.example.blognest.service.PostService;
import org.example.blognest.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;
    private final UserService userService;

    // 새로운 포스트 작성
    @PostMapping("/create")
    public ResponseEntity<Post> createPost(@RequestBody Post post, @SessionAttribute("loggedInUser") String username) {
        Optional<User> user = userService.findByUsername(username);
        if (user.isPresent()) {
            post.setAuthor(user.get());
            Post createdPost = postService.create(post);
            return ResponseEntity.ok(createdPost);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    // 포스트 수정
    @PutMapping("/update/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post postDetails, @SessionAttribute("loggedInUser") String username) {
        Optional<Post> post = postService.getPostById(id);
        if (post.isPresent()) {
            Post updatedPost = post.get();
            updatedPost.setTitle(postDetails.getTitle());
            updatedPost.setContent(postDetails.getContent());
            updatedPost.setPublished(postDetails.isPublished());
            updatedPost.setPublicPost(postDetails.isPublicPost());
            postService.updatePost(updatedPost);
            return ResponseEntity.ok(updatedPost);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    // 포스트 ID로 포스트 찾기
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        Optional<Post> post = postService.getPostById(id);
        return post.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(404).body(null));
    }

    // 작성자 -> 포스트 목록 가져오기
    @GetMapping("/author/{username}")
    public ResponseEntity<List<Post>> getPostsByAuthor(@PathVariable String username) {
        Optional<User> user = userService.findByUsername(username);
        if (user.isPresent()) {
            List<Post> posts = postService.getPostsByAuthor(user.get());
            return ResponseEntity.ok(posts);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    // 임시 글 목록 가져오기
    @GetMapping("/drafts")
    public ResponseEntity<List<Post>> getDrafts(@SessionAttribute("loggedInUser") String username) {
        Optional<User> user = userService.findByUsername(username);
        if (user.isPresent()) {
            List<Post> drafts = postService.getDraftsByAuthor(user.get());
            return ResponseEntity.ok(drafts);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    // 출간된 글 목록 가져오기
    @GetMapping("/published")
    public ResponseEntity<List<Post>> getPublishedPosts() {
        List<Post> posts = postService.getPublishedPosts();
        return ResponseEntity.ok(posts);
    }

    // 모든 포스트 가져오기
    @GetMapping("/all")
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }
}
