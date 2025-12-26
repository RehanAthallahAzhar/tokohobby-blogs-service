package com.tokohobby.blogs.dto;

import com.tokohobby.blogs.entity.BlogStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BlogResponse {
    private Long id;
    private String authorId;
    private String title;
    private String slug;
    private String content;
    private BlogStatus status;
    private String youtubeLink;
    private String imagePath;
    
    // Category & Tags
    private String categoryName;
    private java.util.List<String> tags;
    
    private String previewToken;

    private LocalDateTime publishedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
