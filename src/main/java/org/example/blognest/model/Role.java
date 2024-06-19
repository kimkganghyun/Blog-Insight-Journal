package org.example.blognest.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"users"})
@Table(name = "roles")
public class Role {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // 역할 이름

    @ManyToMany(mappedBy = "roles")
    private Set<User> users; // 이 역할을 가진 사용자 목록
}
