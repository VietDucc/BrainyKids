package com.example.demo.controller;

import com.example.demo.dto.request.ChallengeRequest;
import com.example.demo.dto.request.LessonRequest;
import com.example.demo.entity.Challenge;
import com.example.demo.entity.Lesson;
import com.example.demo.service.ChallengeService;
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
    @GetMapping("/unit/{unitId}")
    public List<Lesson> getLessonByUnitId(@PathVariable Long unitId) {
        return lessonService.getLessonByUnitId(unitId);
    }

    @PostMapping("/unit/{unitId}")
    public ResponseEntity<Lesson> createLesson(@PathVariable Long unitId, @RequestBody LessonRequest lessonRequest) {
        Lesson newLesson = lessonService.createLesson(unitId, lessonRequest);
        return ResponseEntity.ok(newLesson);
    }

    /**
     * getChallengeByLessonId
     * @param lessonId
     * @return
     */
    @GetMapping("/lesson/{lessonId}")
    public List<Challenge> getChallengeByLessonId(@PathVariable Long lessonId) {
        return lessonService.getChallengeByLessonId(lessonId);
    }
}
