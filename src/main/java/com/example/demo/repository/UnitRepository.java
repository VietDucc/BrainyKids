package com.example.demo.repository;

import com.example.demo.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
    List<Unit> findByCourseId(Long courseId);
}
