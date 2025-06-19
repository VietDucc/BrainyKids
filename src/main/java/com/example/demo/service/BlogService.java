package com.example.demo.service;

import com.example.demo.dto.request.BlogRequest;
import com.example.demo.dto.response.BlogResponse;
import com.example.demo.entity.Blog;
import com.example.demo.entity.User;
import com.example.demo.repository.BlogRepository;
import com.example.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository blogRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public BlogResponse createBlog(BlogRequest request, String authorId) {

        ZonedDateTime zonedDateTime = Instant.now().atZone(ZoneId.systemDefault());
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();

        Blog blog = Blog.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .imageUrl(request.getImageUrl())
                .authorId(authorId)
                .createdAt(localDateTime)
                .updatedAt(localDateTime)
                .build();
        blog = blogRepository.save(blog);
        return mapToResponse(blog);
    }

    public BlogResponse editBlog(Long id, BlogRequest request, String authorId) {

        ZonedDateTime zonedDateTime = Instant.now().atZone(ZoneId.systemDefault());
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();

        Blog blog = blogRepository.findById(id).orElse(null);

        if (!blog.getAuthorId().equals(authorId)) {
            throw new RuntimeException("You are not allowed to edit this blog");
        }

        blog.setTitle(request.getTitle());
        blog.setContent(request.getContent());
        blog.setImageUrl(request.getImageUrl());
        blog.setUpdatedAt(localDateTime);
        blog = blogRepository.save(blog);

        return mapToResponse(blog);
    }

    public void deleteBlog(Long id, String authorId) {
        Blog blog = blogRepository.findById(id).orElse(null);

        if (!blog.getAuthorId().equals(authorId)) {
            throw new RuntimeException("You are not allowed to delete this blog");
        }

        blogRepository.delete(blog);
    }

    public List<BlogResponse> getAllBlogs() {
        List<Blog> blogs = blogRepository.findAll();
        return blogs.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public BlogResponse getBlogById(Long blogId) {
        Blog blog = blogRepository.findById(blogId).orElse(null);
        assert blog != null;
        return mapToResponse(blog);
    }

    private BlogResponse mapToResponse(Blog blog) {
        User author = userRepository.findByClerkUserId(blog.getAuthorId()).orElse(null);

        String authorName = author != null
                ? author.getFirstName() + " " + author.getLastName()
                : "Unknown Author";

        String authorImg = author != null
                ? author.getProfile_image_url()
                : "";

        return BlogResponse.builder()
                .id(blog.getId())
                .title(blog.getTitle())
                .content(blog.getContent())
                .imageUrl(blog.getImageUrl())
                .createdAt(blog.getCreatedAt())
                .updatedAt(blog.getUpdatedAt())
                .authorId(blog.getAuthorId())
                .authorImg(authorImg)
                .authorName(authorName)
                .build();
    }
}
