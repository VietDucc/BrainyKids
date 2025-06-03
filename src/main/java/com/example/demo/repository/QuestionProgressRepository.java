package com.example.demo.repository;

import com.example.demo.entity.QuestionProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuestionProgressRepository extends JpaRepository<QuestionProgress, Long> {
    List<QuestionProgress> findByQuestion_Id(Long questionId);

    List<QuestionProgress> findByUserId(String userId);

    @Query("SELECT qp FROM QuestionProgress qp WHERE qp.userId = :userId AND qp.question.id = :questionId")
    Optional<QuestionProgress> findByUserIdAndQuestionId(@Param("userId") String userId, @Param("questionId") Long questionId);

    @Query("SELECT qp FROM QuestionProgress qp WHERE qp.userId = :userId AND qp.question.part.exam.id = :examId")
    List<QuestionProgress> findByUserIdAndExamId(@Param("userId") String userId, @Param("examId") Long examId);
}