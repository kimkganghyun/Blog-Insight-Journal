package org.example.blognest.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"comments", "likes", "tags"})
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId; // 포스트 ID

    @Column(nullable = false)
    private String title; // 포스트 제목

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; // 포스트 내용

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt; // 생성 시간

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt; // 수정 시간

    @Column(nullable = false)
    private boolean published; // 출간 여부

    @Column(name = "public_post", nullable = false)
    private boolean publicPost; // 공개 여부

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User author; // 포스트 작성자

    @OneToMany(mappedBy = "post")
    private List<Comment> comments; // 포스트에 달린 댓글 목록

    @OneToMany(mappedBy = "post")
    private List<Like> likes; // 포스트에 대한 좋아요 목록

    @ManyToMany
    @JoinTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags; // 포스트에 달린 태그 목록

    @Column(nullable = false)
    private boolean draft; // 임시 저장 여부
}
