package com.example.demo.repository;

import com.example.demo.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UnitRepository extends JpaRepository<Unit, Long> {
    List<Unit> findByCourseId(Long courseId);
}
