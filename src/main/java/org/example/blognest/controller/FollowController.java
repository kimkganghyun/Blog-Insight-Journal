package org.example.blognest.controller;

import lombok.RequiredArgsConstructor;
import org.example.blognest.model.Follow;
import org.example.blognest.model.User;
import org.example.blognest.service.FollowService;
import org.example.blognest.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follows")
public class FollowController {

    private final FollowService followService;
    private final UserService userService;

    // 새로운 팔로우 추가
    @PostMapping("/follow")
    public ResponseEntity<Follow> followUser(@RequestParam String followerUsername, @RequestParam String followeeUsername) {
        Optional<User> follower = userService.findByUsername(followerUsername);
        Optional<User> followee = userService.findByUsername(followeeUsername);
        if (follower.isPresent() && followee.isPresent()) {
            Follow follow = new Follow();
            follow.setFollower(follower.get());
            follow.setFollowee(followee.get());
            Follow createdFollow = followService.addFollow(follow);
            return ResponseEntity.ok(createdFollow);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    // 팔로우 취소
    @DeleteMapping("/unfollow")
    public ResponseEntity<Void> unfollowUser(@RequestParam String followerUsername, @RequestParam String followeeUsername) {
        Optional<User> follower = userService.findByUsername(followerUsername);
        Optional<User> followee = userService.findByUsername(followeeUsername);
        if (follower.isPresent() && followee.isPresent()) {
            followService.removeFollow(follower.get(), followee.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    // 사용자의 팔로워 목록 조회
    @GetMapping("/followers/{username}")
    public ResponseEntity<List<User>> getFollowers(@PathVariable String username) {
        Optional<User> user = userService.findByUsername(username);
        if (user.isPresent()) {
            List<User> followers = followService.getFollowers(user.get());
            return ResponseEntity.ok(followers);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    // 사용자가 팔로우한 목록 조회
    @GetMapping("/following/{username}")
    public ResponseEntity<List<User>> getFollowing(@PathVariable String username) {
        Optional<User> user = userService.findByUsername(username);
        if (user.isPresent()) {
            List<User> following = followService.getFollowing(user.get());
            return ResponseEntity.ok(following);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }
}
