package com.example.demo.dto.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamDTO {
    private Long id;
    private String name;
    private String description;
    private String voice;
    private List<PartDTO> parts;
}