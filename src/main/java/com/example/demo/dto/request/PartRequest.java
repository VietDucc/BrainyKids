package com.example.demo.dto.request;

import com.example.demo.enums.PartType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartRequest {
    private String description;
    private int partOrder;
    private int partNumber;
    private PartType type;
    private long examId;
}