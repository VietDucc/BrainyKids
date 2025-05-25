package com.example.demo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class UnitResponse {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LessonDTO {
        private Long id;
        private String title;
        private int orderLesson;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UnitDTO {
        private Long id;
        private String title;
        private String description;
        private int orderUnit;
        private List<LessonDTO> lessons;
    }
}
