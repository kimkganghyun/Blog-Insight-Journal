package org.example.blognest.repository;

import org.example.blognest.model.Follow;
import org.example.blognest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

    void deleteByFollowerAndFollowee(User follower, User followee);

    // 사용자를 팔로우한 목록 찾기
    @Query("SELECT f FROM Follow f WHERE f.followee = :user")
    List<Follow> findByFollowee(User user);

    // 사용자가 팔로우한 목록 찾기
    @Query("SELECT f FROM Follow f WHERE f.follower = :user")
    List<Follow> findByFollower(User user);
}
