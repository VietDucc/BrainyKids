package com.example.demo.dto.response;

import lombok.Builder;
import lombok.Getter;
import java.util.List;
import java.time.LocalDateTime;

@Getter
@Builder
public class BlogResponse {
    private Long id;
    private String title;
    private String content;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String authorId;
    private List<CommentResponse> comments;
}
