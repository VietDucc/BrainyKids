package com.example.demo.repository;

import com.example.demo.entity.Challenge;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    List<Challenge> findByLesson_Id(Long LessonId);

    @Query(value = "SELECT COALESCE(MAX(id), 0)  FROM chanllenges", nativeQuery = true)
    Long findMaxId();

    @Query("SELECT DISTINCT c FROM Challenge c LEFT JOIN FETCH c.challengesOption WHERE c.lesson.id = :lessonId")
    List<Challenge> findByLessonIdWithOptions(@Param("lessonId") Long lessonId);

}
