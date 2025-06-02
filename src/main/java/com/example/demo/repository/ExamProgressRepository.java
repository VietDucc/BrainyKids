package com.example.demo.repository;

import com.example.demo.entity.ExamProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ExamProgressRepository extends JpaRepository<ExamProgress, Long> {
    List<ExamProgress> findByUserId(String userId);
    List<ExamProgress> findByExam_Id(Long examId);
    Optional<ExamProgress> findByUserIdAndExam_Id(String userId, Long examId);
}