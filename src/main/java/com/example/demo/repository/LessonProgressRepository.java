package com.example.demo.repository;

import com.example.demo.dto.response.LessonProgressResponse;
import com.example.demo.entity.LessonProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonProgressRepository extends JpaRepository<LessonProgress, Long> {
    List<LessonProgress> findByUser_ClerkUserIdAndLesson_Id(String clerkUserId, long lessonId);
    List <LessonProgress> findByUser_ClerkUserId(String clerkUserId);
}
