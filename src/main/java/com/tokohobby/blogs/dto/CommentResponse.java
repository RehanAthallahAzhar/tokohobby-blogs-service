package com.tokohobby.blogs.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

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
    private String replyToUserId;
    @Builder.Default
    private List<CommentResponse> replies = new ArrayList<>();
}