package org.example.blognest.service;

import lombok.RequiredArgsConstructor;
import org.example.blognest.model.Follow;
import org.example.blognest.model.User;
import org.example.blognest.repository.FollowRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;

    // 새로운 팔로우 추가
    public Follow addFollow(Follow follow) {
        return followRepository.save(follow);
    }

    // 팔로우 취소
    public void removeFollow(User follower, User followee) {
        followRepository.deleteByFollowerAndFollowee(follower, followee);
    }

    // 사용자의 팔로워 목록 조회
    public List<User> getFollowers(User user) {
        List<Follow> follows = followRepository.findByFollowee(user);
        return follows.stream().map(Follow::getFollower).collect(Collectors.toList());
    }

    // 사용자가 팔로우한 목록 조회
    public List<User> getFollowing(User user) {
        List<Follow> follows = followRepository.findByFollower(user);
        return follows.stream().map(Follow::getFollowee).collect(Collectors.toList());
    }
}
