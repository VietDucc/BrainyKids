package com.example.demo.repository;

import com.example.demo.entity.Question;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByPart_Id(Long partId);

    List<Question> findByPart_Exam_Id(Long examId);

    @Query(value = "SELECT COALESCE(MAX(id), 0) FROM questions", nativeQuery = true)
    Long findMaxId();

    @Query("SELECT DISTINCT q FROM Question q LEFT JOIN FETCH q.questionOptions WHERE q.part.id = :partId")
    List<Question> findByPartIdWithOptions(@Param("partId") Long partId);

    @Query("SELECT DISTINCT q FROM Question q LEFT JOIN FETCH q.questionOptions WHERE q.part.exam.id = :examId")
    List<Question> findByExamIdWithOptions(@Param("examId") Long examId);
}