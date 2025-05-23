package com.example.demo.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LessonProgressRequest {
    private String clerkUserId;
    private long lessonId;
    private boolean completed;

}