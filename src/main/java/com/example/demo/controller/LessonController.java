package com.example.demo.controller;

import com.example.demo.dto.request.LessonRequest;
import com.example.demo.entity.Lesson;
import com.example.demo.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {
    @Autowired
    private LessonService lessonService;

    //Lay danh sach theo unit id
    @GetMapping("/{unitId}")
    public List<Lesson> getLessonByUnitId(@PathVariable Long unitId) {
        return lessonService.getLessonByUnitId(unitId);
    }

    @PostMapping("/{unitId}")
    public ResponseEntity<Lesson> createLesson(@PathVariable Long unitId, @RequestBody LessonRequest lessonRequest) {
        Lesson newLesson = lessonService.createLesson(unitId, lessonRequest);
        return ResponseEntity.ok(newLesson);
    }
}
