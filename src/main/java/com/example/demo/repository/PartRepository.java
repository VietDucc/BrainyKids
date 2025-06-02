package com.example.demo.repository;

import com.example.demo.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartRepository extends JpaRepository<Part, Long> {
    List<Part> findByExam_Id(Long examId);
}