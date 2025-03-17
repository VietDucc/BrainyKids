package com.example.demo.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LessonProgressResponse {
    private Long id;
    private boolean completed;
    private Long lessonId;
    private String clerkUserId;

    public LessonProgressResponse(Long id, boolean completed, Long lessonId, String clerkUserId) {
        this.id = id;
        this.completed = completed;
        this.lessonId = lessonId;
        this.clerkUserId = clerkUserId;
    }
}