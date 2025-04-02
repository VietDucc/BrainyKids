package com.example.demo.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class LessonProgressResponse {
    private Long id;
    private boolean completed;
    private Long lessonId;
    private String clerkUserId;
    private Date dateLesson;

    public LessonProgressResponse(Long id, boolean completed, Long lessonId, String clerkUserId, Date dateLesson) {
        this.id = id;
        this.completed = completed;
        this.lessonId = lessonId;
        this.clerkUserId = clerkUserId;
        this.dateLesson = dateLesson;
    }
}