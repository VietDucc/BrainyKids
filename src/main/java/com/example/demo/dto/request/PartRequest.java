package com.example.demo.dto.request;

import com.example.demo.enums.PartType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PartRequest {
    private String description;
    private int partOrder;
    private int partNumber;
    private PartType type;
    private long examId;
}