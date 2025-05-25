package com.example.demo.controller;

import com.example.demo.dto.request.LessonRequest;
import com.example.demo.dto.response.ChallengeDTO;
import com.example.demo.dto.response.LessonDetailResponse;
import com.example.demo.entity.Challenge;
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
    @GetMapping("/unit/{unitId}")
    public List<Lesson> getLessonByUnitId(@PathVariable Long unitId) {
        return lessonService.getLessonByUnitId(unitId);
    }

    @PostMapping("/unit/{unitId}")
    public ResponseEntity<Lesson> createLesson(@PathVariable Long unitId, @RequestBody LessonRequest lessonRequest) {
        Lesson newLesson = lessonService.createLesson(unitId, lessonRequest);
        return ResponseEntity.ok(newLesson);
    }
    // Cập nhật bài học
    @PutMapping("/{lessonId}")
    public ResponseEntity<Lesson> updateLesson(@PathVariable Long lessonId, @RequestBody LessonRequest lessonRequest) {
        Lesson updatedLesson = lessonService.updateLesson(lessonId, lessonRequest);
        return ResponseEntity.ok(updatedLesson);
    }

    // Xóa bài học
    @DeleteMapping("/{lessonId}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long lessonId) {
        lessonService.deleteLesson(lessonId);
        return ResponseEntity.noContent().build();
    }

    /**
     * getChallengeByLessonId
     * @param lessonId
     * @return
     */
    @GetMapping("/lesson/{lessonId}")
    public LessonDetailResponse getChallengeByLessonId(@PathVariable Long lessonId) {
        lessonService.cacheLessonAdnNext10(lessonId);

        return lessonService.getLessonDetailByLessonId(lessonId);
    }
}
