package org.example.blognest.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"posts", "follows", "roles"})
@Table(name = "users")
public class User {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 아이디

    @Column(unique = true, nullable = false)
    private String username; // 사용자 이름

    @Column(nullable = false)
    private String password; // 사용자 비밀번호

    @Column(nullable = false)
    private String name; // 사용자 설명

    @Column(nullable = false)
    private String email; // 사용자 이메일

    @Column(name = "registration_date", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt; // 등록 날짜

    private String profilePicture; // 사용자 프로필 이미지 URL

    private String blogTitle; // 블로그 제목 ( 기본값 : 사용자 아이디 )

    private boolean emailVerified; // 이메일 알림 설정

    @OneToMany(mappedBy = "author")
    private List<Post> posts; // 사용자가 작성한 포스트 목록

    @OneToMany(mappedBy = "follower")
    private List<Follow> followers; // 사용자가 필로우한 사용자 목록

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles; // 사용자의 역할 목록

    @Column(nullable = true)
    private String description; // 사용자 자기 소개

}
