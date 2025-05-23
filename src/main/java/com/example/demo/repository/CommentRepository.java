package com.example.demo.repository;

import com.example.demo.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBlogId(Long blogId);
    List<Comment> findAllByParentCommentId(Long parentCommentId);
    List<Comment> findByBlogId(Long blogId);
    List<Comment> findByBlogIdAndParentCommentIsNull(Long blogId);
    List<Comment> findByParentCommentId(Long parentCommentId);
}
