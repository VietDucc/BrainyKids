package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;// PostMapping dinh nghia mot API endpoint su dung HTTP POST
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;// RequestBody cho phep Spring boot tu dong anh sa JSON tu request vao mot doi tuong java
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;//RestController: danh dau class nay la mot REST Controller, giup Spring boot hieu rang no se xu li HTTP request va tra ve JSON/XML

import com.example.demo.dto.request.UserCreationRequest;
import com.example.demo.dto.request.UserUpdateRequest;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/users")
// Controller: Nhan request va goi Service
public class UserController {
    @Autowired // Autowired: SP se tu dong inject mot instance cua UserService vao ma ko can
               // khoi tao thu
    private UserService userService;

    //@RequestBody: chuyen doi  du lieu JSON tu phan than body cua request
    //thanh doi tuong UserCreationRequest
    //@Valid: kiem tra rang buoc cua doi tuong UserCreationRequest
    @PostMapping
    public User createRequest(@RequestBody @Valid UserCreationRequest request) {// @RequestBody: giup SP chuyen doi JSon// tu
        return userService.createRequest(request);
    }

    @GetMapping
    public List<User> getUserss() {
        return userService.getUsers();
    }

    // Lay thong tin dua tren ID dong "http://localhost:8080/demo/users/52"
    @GetMapping("/{userId}")
    User getUserById(@PathVariable Long userId) {
        return userService.getUserById(userId);
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    String deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return "User deleted";
    }
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello World";
    }
}
// Hanh trinh du lieu
// Khi nguoi dung gui request POST/ users
// 1. User gui du lieu JSON-> Spring Boot Controller nhan du lieu
// 2. Controller goi Service de xu ly logic
// 3. Service luu du lieu vao MySQL thong qua Repository
// 4. Tra ve ket qua JSON cho nguoi dung bang User

//UserCreationRequest dai dien cho kkieu du lieu dau vao
//Entity du lieu dau ra