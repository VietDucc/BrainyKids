package com.example.demo.repository;

import com.example.demo.entity.TestPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestPartRepository extends JpaRepository<TestPart, Long> {
}
