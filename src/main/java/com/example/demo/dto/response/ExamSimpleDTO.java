package com.example.demo.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExamSimpleDTO {
    private Long id;
    private String name;
    private String description;
    private String voice;
}