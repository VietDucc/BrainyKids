package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "blogs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;
    private String imageUrl;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private String authorId;

    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;
}
