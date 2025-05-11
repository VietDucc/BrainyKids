package com.example.demo.dto.request;

import com.example.demo.enums.Difficulty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LessonRequest {
    private String title;
    private Difficulty difficulty;
    private int orderIndex;
}
