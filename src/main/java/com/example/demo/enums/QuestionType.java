package com.example.demo.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum QuestionType {
    part1, // 1 anh, 4 cau tra loi
    part2, // 1 cau hoi, 3 cau tra loi
    part3, // 1 cau hoi, 4 text || 1 anh tong 1 cau hoi 4 cau tra loi
    part5, // 1 cau hoi, 4 cau tra loi chu
    part6; // 1 cau hoi, 1 anh, 4 cau tra loi

    @JsonCreator
    public static QuestionType fromString(String value) {
        if (value == null) {
            return null;
        }

        return Arrays.stream(QuestionType.values())
                .filter(type -> type.name().equalsIgnoreCase(value.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown question type: " + value));
    }
}
