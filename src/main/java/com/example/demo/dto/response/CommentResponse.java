package com.example.demo.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class CommentResponse {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CommentResponse> replies;
    private Long blogId;
    private Long parentCommentId;

    private String authorId;
    private String authorName;
    private String authorImg;
}
