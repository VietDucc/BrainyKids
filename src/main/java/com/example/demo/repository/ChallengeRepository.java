package com.example.demo.repository;

import com.example.demo.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    List<Challenge> findByLesson_Id(Long LessonId);

    @Query(value = "SELECT COALESCE(MAX(id), 0)  FROM chanllenges", nativeQuery = true)
    Long findMaxId();

}
