package com.example.demo.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ExamRequest {
    private String name;
    private String description;
    private String voice;
}