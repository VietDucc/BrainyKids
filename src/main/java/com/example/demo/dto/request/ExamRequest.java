package com.example.demo.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamRequest {
    private String name;
    private String description;
    private String voice;
}