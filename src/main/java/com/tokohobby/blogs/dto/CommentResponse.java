package com.tokohobby.blogs.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private Long id;
    private String content;
    private Long blogId;
    private String userId;
    private Long likes;
    private Long dislikes;
    private LocalDateTime createdAt;
    private String currentVote; // LIKE, DISLIKE, or null
    private List<CommentResponse> replies;
}