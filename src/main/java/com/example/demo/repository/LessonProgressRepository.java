package com.example.demo.repository;

import com.example.demo.dto.response.LessonProgressResponse;
import com.example.demo.entity.LessonProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface LessonProgressRepository extends JpaRepository<LessonProgress, Long> {
    List<LessonProgress> findByUser_ClerkUserIdAndLesson_Id(String clerkUserId, long lessonId);
    List <LessonProgress> findByUser_ClerkUserId(String clerkUserId);
    @Query("SELECT MAX(lp.dateLesson) FROM LessonProgress lp WHERE lp.user.clerkUserId = :clerkUserId AND lp.completed = true")
    Optional<Date> findLastCompletedDate(String clerkUserId);
}
