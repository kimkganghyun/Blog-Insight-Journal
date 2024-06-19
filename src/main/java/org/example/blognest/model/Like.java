package org.example.blognest.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "likes")
public class Like {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 좋아요 ID

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;  // 좋아요를 한 사용자

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;  // 좋아요를 받은 포스트
}
