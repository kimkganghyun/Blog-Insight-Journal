package org.example.blognest.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"posts"})
public class Tag {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // 태그 ID

    @Column(nullable = false, unique = true)
    private String name;  // 태그 이름

    @ManyToMany(mappedBy = "tags")
    private List<Post> posts;  // 태그가 달린 포스트 목록
}
