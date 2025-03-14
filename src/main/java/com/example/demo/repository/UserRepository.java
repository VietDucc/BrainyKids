package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

@Repository
// Tự động có các phương thức như save(), findById(), delete(), findAll().
// JpaRepository<User, Long>: User là Entity, Long là kiểu dữ liệu của khóa
// chính của Entity.
public interface UserRepository extends JpaRepository<User, Long> {


}
