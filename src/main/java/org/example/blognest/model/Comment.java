package org.example.blognest.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.blognest.model.Post;
import org.example.blognest.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"post","replies", "parent"})
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 댓글 ID

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;  // 댓글 내용

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;  // 생성 시간

    @Column(name = "updated_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;  // 댓글 작성자

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;  // 댓글이 달린 포스트

    @OneToMany(mappedBy = "parent")
    private List<Comment> replies;  // 대댓글 목록

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comment parent;  // 부모 댓글

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
