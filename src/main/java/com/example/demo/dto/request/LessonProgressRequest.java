package com.example.demo.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class LessonProgressRequest {
    private String clerkUserId;
    private long lessonId;
    private boolean completed;

}