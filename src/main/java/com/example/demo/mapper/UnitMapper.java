package com.example.demo.mapper;

import com.example.demo.dto.response.UnitResponse;
import com.example.demo.entity.Unit;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UnitMapper {

    public static UnitResponse.UnitDTO toDTO(Unit unit) {
        if (unit == null) {
            return null;
        }

        List<UnitResponse.LessonDTO> lessonDTOs = unit.getLessons() != null
                ? unit.getLessons().stream()
                .map(lesson -> new UnitResponse.LessonDTO(
                        lesson.getId(),
                        lesson.getTitle(),
                        lesson.getOrderIndex()
                ))
                .collect(Collectors.toList())
                : Collections.emptyList();

        return new UnitResponse.UnitDTO(
                unit.getId(),
                unit.getTitle(),
                unit.getDescription(),
                unit.getOrderUnit(),
                lessonDTOs
        );
    }
}
