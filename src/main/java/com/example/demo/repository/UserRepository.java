package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.User;

@Repository
// Tự động có các phương thức như save(), findById(), delete(), findAll().
// JpaRepository<User, Long>: User là Entity, Long là kiểu dữ liệu của khóa
// chính của Entity.
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring lam sao biet ghi ten ma biet ta can lam gi la do: spring data JPA su
    // dung phan tich cu phap(parsing) tren ten cua phuong thuc ta khai bao
    // No chia nhno existByName ra thanh
    // + existBy: bieu thi rang ta muon kiem tra su ton tai trogn SQL
    // + Name: la truong name cua entity User
    boolean existsByName(String name);
}
