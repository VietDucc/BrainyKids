package com.example.demo.controller;

import com.example.demo.dto.request.LessonProgressRequest;
import com.example.demo.dto.response.LessonProgressResponse;
import com.example.demo.entity.LessonProgress;
import com.example.demo.repository.LessonProgressRepository;
import com.example.demo.service.LessonProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lesson-progress")
public class LessonProgressController {
    @Autowired
    private LessonProgressService lessonProgressService;

    @PostMapping
    public ResponseEntity<String> updateLessonProgress(@RequestBody LessonProgressRequest lessonProgressRequest) {
        LessonProgress updatedProgress = lessonProgressService.updateLessonProgress(
                lessonProgressRequest.getClerkUserId(),
                lessonProgressRequest.getLessonId(),
                lessonProgressRequest.isCompleted()
        );
        return ResponseEntity.ok("Lesson progress updated successfully");
    }
    //Lay danh sach lesson da hoc cua user
    @GetMapping("/{clerkUserId}")
    public List<LessonProgressResponse> getLessonProgress(@PathVariable String clerkUserId) {
        return lessonProgressService.getLessonProgressByUserId(clerkUserId);
    }

}
