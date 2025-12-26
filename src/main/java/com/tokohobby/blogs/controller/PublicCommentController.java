package com.tokohobby.blogs.controller;

import com.tokohobby.blogs.service.CommentService;
import com.tokohobby.blogs.entity.Comment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.tokohobby.blogs.dto.*;

import java.util.List;


@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@Slf4j
public class PublicCommentController {
    private final CommentService commentService;

    @GetMapping("/blog/{blogId}")
    public List<CommentResponse> getComments(
        @PathVariable Long blogId, 
        @RequestHeader(value = "X-Visitor-Id", required = false) String visitorId) {
        return commentService.getCommentsByBlogId(blogId, visitorId);
    }

    @GetMapping("/{id}")
    public CommentResponse getCommentById(@PathVariable Long id) {
        return commentService.getCommentById(id);
    }

    @PostMapping
    public CommentResponse createComment(@RequestBody CommentRequest request) {
        log.info("REST request to create comment for blogId: {}", request.getBlogId());
        return commentService.createComment(request);
    }

    @PutMapping("/{id}")
    public CommentResponse updateComment(@PathVariable Long id, @RequestBody CommentRequest request) {
        return commentService.updateComment(id, request.getContent());
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
    }

    @PostMapping("/{id}/like")
    public CommentResponse likeComment(
        @PathVariable Long id,
        @RequestHeader(value = "X-Visitor-Id") String visitorId) {
        return commentService.likeComment(id, visitorId);
    }

    @PostMapping("/{id}/dislike")
    public CommentResponse dislikeComment(
        @PathVariable Long id,
        @RequestHeader(value = "X-Visitor-Id") String visitorId) {
        return commentService.dislikeComment(id, visitorId);
    }
}
