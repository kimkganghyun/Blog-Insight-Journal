package org.example.blognest.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "follows")
public class Follow {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 팔로워 ID

    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User follower;  // 팔로우를 한 사용자

    @ManyToOne
    @JoinColumn(name = "followee_id")
    private User followee;  // 팔로우를 받은 사용자

}
