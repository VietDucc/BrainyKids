package com.example.demo.service;

import com.example.demo.dto.request.CommentRequest;
import com.example.demo.dto.response.CommentResponse;
import com.example.demo.entity.Blog;
import com.example.demo.entity.Comment;
import com.example.demo.repository.BlogRepository;
import com.example.demo.repository.CommentRepository;
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
public class CommentService {

    private final CommentRepository commentRepository;
    private final BlogRepository blogRepository;

    public CommentResponse createComment(Long blogId, CommentRequest request, String authorId) {

        ZonedDateTime zonedDateTime = Instant.now().atZone(ZoneId.systemDefault());
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();

        Blog blog = blogRepository.findById(blogId).orElse(null);

        Comment comment = Comment.builder()
                .content(request.getContent())
                .blog(blog)
                .authorId(authorId)
                .createdAt(localDateTime)
                .updatedAt(localDateTime)
                .build();

        comment = commentRepository.save(comment);

        return mapToResponse(comment);
    }

    public CommentResponse replyToComment(Long commentId, CommentRequest request, String authorId) {

        ZonedDateTime zonedDateTime = Instant.now().atZone(ZoneId.systemDefault());
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();

        Comment parentComment = commentRepository.findById(commentId).orElse(null);

        Comment reply = Comment.builder()
                .content(request.getContent())
                .blog(parentComment.getBlog())
                .parentComment(parentComment)
                .authorId(authorId)
                .createdAt(localDateTime)
                .updatedAt(localDateTime)
                .build();

        reply = commentRepository.save(reply);

        return mapToResponse(reply);
    }

    public CommentResponse editComment(Long id, CommentRequest request, String authorId) {

        ZonedDateTime zonedDateTime = Instant.now().atZone(ZoneId.systemDefault());
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();

        Comment comment = commentRepository.findById(id).orElse(null);

        if (!comment.getAuthorId().equals(authorId)) {
            throw new RuntimeException("You are not allowed to edit this comment");
        }

        comment.setContent(request.getContent());
        comment.setUpdatedAt(localDateTime);
        comment.setAuthorId(authorId);
        commentRepository.save(comment);

        return mapToResponse(comment);
    }

    public List<CommentResponse> getCommentsByBlogId(Long blogId) {
        List<Comment> comments = commentRepository.findByBlogId(blogId);
        return comments.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }


    public List<CommentResponse> getCommentsByBlogIdWithReplies(Long blogId) {
        List<Comment> topLEvelComments = commentRepository.findByBlogIdAndParentCommentIsNull(blogId);

        return topLEvelComments.stream()
                .map(this::mapToResponseWithReplies)
                .collect(Collectors.toList());
    }

    public void deleteComment(Long commentId, String authorId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);

        if (!comment.getAuthorId().equals(authorId)) {
            throw new RuntimeException("You are not allowed to delete this comment");
        }

        commentRepository.delete(comment);
    }


    private CommentResponse mapToResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .authorId(comment.getAuthorId())
                .blogId(comment.getBlog() == null ? null : comment.getBlog().getId())
                .parentCommentId(comment.getParentComment() == null ? null : comment.getParentComment().getId())
                .build();
    }

    public CommentResponse mapToResponseWithReplies(Comment comment) {
        List<Comment> replies = commentRepository.findByParentCommentId(comment.getId());

        CommentResponse.CommentResponseBuilder builder = CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getUpdatedAt())
                .authorId(comment.getAuthorId())
                .blogId(comment.getBlog() != null ? comment.getBlog().getId() : null)
                .parentCommentId(comment.getParentComment() == null ? null : comment.getParentComment().getId());
        if (!replies.isEmpty()) {
            List<CommentResponse> replyResponses = replies.stream()
                    .map(this::mapToResponseWithReplies)
                    .collect(Collectors.toList());
            builder.replies(replyResponses);
        }
        return builder.build();
    }

}
