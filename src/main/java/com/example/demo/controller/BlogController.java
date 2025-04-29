package com.example.demo.controller;

import com.example.demo.dto.request.BlogRequest;
import com.example.demo.dto.response.BlogResponse;
import com.example.demo.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
@RequiredArgsConstructor
public class BlogController {

    private final BlogService blogService;

    @PostMapping
    public ResponseEntity<BlogResponse> createBlog(
            @RequestBody BlogRequest request,
            @RequestHeader("clerkUserId") String authorId) {
        BlogResponse blog = blogService.createBlog(request, authorId);
        return ResponseEntity.ok(blog);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogResponse> editBlog(
            @PathVariable Long id,
            @RequestBody BlogRequest request,
            @RequestHeader("clerkUserId") String authorId) {
        BlogResponse blog = blogService.editBlog(id, request, authorId);
        return ResponseEntity.ok(blog);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBlog(
            @PathVariable Long id,
            @RequestHeader("clerkUserId") String authorId) {
        blogService.deleteBlog(id, authorId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<BlogResponse>> getAllBlogs() {
        List<BlogResponse> blogs = blogService.getAllBlogs();
        return ResponseEntity.ok(blogs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogResponse> getBlogById(@PathVariable Long id) {
        BlogResponse blog = blogService.getBlogById(id);
        return ResponseEntity.ok(blog);
    }
}
