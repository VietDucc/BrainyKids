package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
// Tự động có các phương thức như save(), findById(), delete(), findAll().
// JpaRepository<User, Long>: User là Entity, Long là kiểu dữ liệu của khóa
// chính của Entity.
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByClerkUserId(String clerkUserId);
    List<User> findTop10ByOrderByScoreDesc();
}
