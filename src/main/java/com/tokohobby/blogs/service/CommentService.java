package com.tokohobby.blogs.service;

import com.tokohobby.blogs.entity.Comment;
import com.tokohobby.blogs.entity.Blog;
import com.tokohobby.blogs.entity.CommentEngagement;
import com.tokohobby.blogs.repository.CommentRepository;
import com.tokohobby.blogs.repository.BlogRepository;
import com.tokohobby.blogs.repository.CommentEngagementRepository;
import com.tokohobby.blogs.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {
    private final CommentRepository commentRepository;
    private final BlogRepository blogRepository;
    private final CommentEngagementRepository engagementRepository;

    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentsByBlogId(Long blogId, String userId) {
        return commentRepository.findByBlogIdAndParentIsNullOrderByCreatedAtDesc(blogId).stream()
                .map(comment -> mapToDTOWithEngagement(comment, userId))
                .collect(Collectors.toList());
    }
    private CommentResponse mapToDTOWithEngagement(Comment comment, String userId) {
        CommentResponse res = mapToDTO(comment);
        
        // check if user has already liked/disliked
        engagementRepository.findByUserIdAndCommentId(userId, comment.getId())
            .ifPresent(eng -> res.setCurrentVote(eng.getType()));
            
        // Map recursive replies
        if (comment.getReplies() != null) {
            res.setReplies(comment.getReplies().stream()
                .map(reply -> mapToDTOWithEngagement(reply, userId))
                .collect(Collectors.toList()));
        }
        
        return res;
    }

    // Get comment by id
    @Transactional(readOnly = true)
    public CommentResponse getCommentById(Long id) {
        return mapToDTO(commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found")));
    }

    // Create comment
    @Transactional
    public CommentResponse createComment(CommentRequest request) {
        log.info("Creating comment for blogId: {}", request.getBlogId());
        Blog blog = blogRepository.findById(request.getBlogId())
                .orElseThrow(() -> new RuntimeException("Blog not found"));
        
        Comment.CommentBuilder builder = Comment.builder()
                .content(request.getContent())
                .blog(blog)
                .userId(request.getUserId() != null ? request.getUserId() : "guest")
                .likes(0L)
                .dislikes(0L);

        if (request.getParentId() != null) {
            Comment target = commentRepository.findById(request.getParentId())
                    .orElseThrow(() -> new RuntimeException("Target comment not found"));
            
            if (target.getParent() != null && target.getParent().getParent() != null) {
                throw new IllegalStateException("Maximum comment depth is 2");
            }

            if (target.getParent() != null) {
                builder.parent(target.getParent());
                builder.replyToUserId(target.getUserId());
            } else {
                builder.parent(target);
            }
        }
                
        return mapToDTO(commentRepository.save(builder.build()));
    }

    // Update comment
    @Transactional
    public CommentResponse updateComment(Long id, String content) {
        log.info("Updating comment with id: {}", id);
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setContent(content);
        Comment updated = commentRepository.save(comment);
        log.info("Successfully updated comment with id: {}", id);
        return mapToDTO(updated);
    }

    // Delete comment
    @Transactional
    public void deleteComment(Long id) {
        log.warn("Deleting comment with id: {}", id);
        commentRepository.deleteById(id);
        log.info("Successfully deleted comment with id: {}", id);
    }

    // Like comment
    @Transactional
    public CommentResponse likeComment(Long id, String userId) {
        log.info("User {} is liking comment {}", userId, id);
        
        // Check if user has already liked/disliked
        if (engagementRepository.existsByUserIdAndCommentId(userId, id)) {
            throw new RuntimeException("You already engaged with this comment");
        }

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        
        comment.setLikes(comment.getLikes() + 1);
        
        // Save engagement record to prevent duplicate engagement
        engagementRepository.save(CommentEngagement.builder()
                .userId(userId)
                .commentId(id)
                .type("LIKE")
                .build());

        return mapToDTO(commentRepository.save(comment));
    }

    // Dislike comment
    @Transactional
    public CommentResponse dislikeComment(Long id, String userId) {
        log.info("User {} is disliking comment {}", userId, id);

        // Check if user has already liked/disliked
        if (engagementRepository.existsByUserIdAndCommentId(userId, id)) {
            throw new RuntimeException("You already engaged with this comment");
        }

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        
        comment.setDislikes(comment.getDislikes() + 1);

        // Save engagement record to prevent duplicate engagement
        engagementRepository.save(CommentEngagement.builder()
                .userId(userId)
                .commentId(id)
                .type("DISLIKE")
                .build());

        return mapToDTO(commentRepository.save(comment));
    }


    // Helper: Map Entity to DTO
    private CommentResponse mapToDTO(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .blogId(comment.getBlog().getId())
                .userId(comment.getUserId())
                .replyToUserId(comment.getReplyToUserId()) // Map field baru
                .likes(comment.getLikes())
                .dislikes(comment.getDislikes())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}