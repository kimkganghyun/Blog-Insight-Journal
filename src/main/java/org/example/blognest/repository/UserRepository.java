package org.example.blognest.repository;

import org.example.blognest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // 사용자 이름으로 사용자 찾기

    Optional<User> findByUsername(String username);

    // 사용자 이메일으로 사용자 찾기
    Optional<User> findByEmail(String email);

    // 아이디 중복 확인 여부
    boolean existsByUsername(String username);

    // 이메일 주소 존재 확인 여부
    boolean existsByEmail(String email);


}
