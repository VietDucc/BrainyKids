package com.example.demo.repository;

import com.example.demo.entity.QuestionOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionOptionRepository extends JpaRepository<QuestionOption, Long> {
    List<QuestionOption> findByQuestion_Id(Long questionId);
    List<QuestionOption> findByIdIn(List<Long> ids);
}