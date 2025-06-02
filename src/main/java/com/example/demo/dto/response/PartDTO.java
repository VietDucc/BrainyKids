package com.example.demo.dto.response;

import com.example.demo.enums.PartType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartDTO {
    private Long id;
    private String description;
    private int partOrder;
    private int partNumber;
    private PartType type;
    private List<QuestionDTO> questions;
}