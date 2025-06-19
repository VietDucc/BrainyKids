package com.example.demo.repository;

import com.example.demo.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    @Query(value = "SELECT COALESCE(MAX(id), 0) FROM exams", nativeQuery = true)
    Long findMaxId();

    @Query("SELECT DISTINCT e FROM Exam e LEFT JOIN FETCH e.parts p LEFT JOIN FETCH p.questions q WHERE e.id = :examId")
    Optional<Exam> findByIdWithQuestionsAndOptions(@Param("examId") Long examId);
}
