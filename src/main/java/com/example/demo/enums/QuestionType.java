package com.example.demo.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

public enum QuestionType {
    exam1, // 4 dap an chu
    exam2, // 4 dap an chi co option, k co text, 1 anh tong
    exam3, // 4 dap an anh
    read1, // 4 dap an chu, 1 anh cau hoi
    read2, // 4 dap an chu, 1 doan hoi thoai
    read3, // 4 dap an anh, 1 cau hoi va anh
    read4, // 4 dap an anh, 1 cau hoi va anh
    read5; // 4 dap an chu hoac anh, 2 cau hoi

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
