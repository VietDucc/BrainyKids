package com.example.demo.controller;

import com.example.demo.dto.request.CommentRequest;
import com.example.demo.dto.response.CommentResponse;
import com.example.demo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/blogs/{blogId}/comments")
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable Long blogId,
            @RequestBody CommentRequest request,
            @RequestHeader("clerkUserId") String authorId) {
        CommentResponse comment = commentService.createComment(blogId, request, authorId);
        return ResponseEntity.ok(comment);
    }

    @PostMapping("/comments/{commentId}/reply")
    public ResponseEntity<CommentResponse> replyToComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequest request,
            @RequestHeader("clerkUserId") String authorId) {
        CommentResponse reply = commentService.replyToComment(commentId, request, authorId);
        return ResponseEntity.ok(reply);
    }

    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponse> editComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequest request,
            @RequestHeader("clerkUserId") String authorId) {
        CommentResponse comment = commentService.editComment(commentId, request, authorId);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/blogs/{blogId}/comments")
    public ResponseEntity<List<CommentResponse>> getCommentsByBlogId(@PathVariable Long blogId) {
        List<CommentResponse> comments = commentService.getCommentsByBlogIdWithReplies(blogId);
        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            @RequestHeader("clerkUserId") String authorId) {
        commentService.deleteComment(commentId, authorId);
        return ResponseEntity.noContent().build();
    }
}
