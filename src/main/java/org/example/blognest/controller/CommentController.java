package org.example.blognest.controller;

import lombok.RequiredArgsConstructor;
import org.example.blognest.model.Comment;
import org.example.blognest.model.Post;
import org.example.blognest.model.User;
import org.example.blognest.service.CommentService;
import org.example.blognest.service.PostService;
import org.example.blognest.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;
    private final UserService userService;

    // 새로운 댓글 작성
    @PostMapping("/create")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment, @RequestParam String username, @RequestParam Long postId) {
        Optional<User> user = userService.findByUsername(username);
        Optional<Post> post = postService.getPostById(postId);
        if (user.isPresent() && post.isPresent()) {
            comment.setAuthor(user.get());
            comment.setPost(post.get());
            Comment createdComment = commentService.create(comment);
            return ResponseEntity.ok(createdComment);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    // 댓글 수정
    @PutMapping("/update/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody Comment commentDetails) {
        Optional<Comment> comment = commentService.getCommentById(id);
        if (comment.isPresent()) {
            Comment updatedComment = comment.get();
            updatedComment.setContent(commentDetails.getContent());
            commentService.updateComment(updatedComment);
            return ResponseEntity.ok(updatedComment);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    // 댓글 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    // 특정 포스트의 댓글 목록 조회
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPost(@PathVariable Long postId) {
        Optional<Post> post = postService.getPostById(postId);
        if (post.isPresent()) {
            List<Comment> comments = commentService.getCommentsByPost(post.get());
            return ResponseEntity.ok(comments);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }
}
