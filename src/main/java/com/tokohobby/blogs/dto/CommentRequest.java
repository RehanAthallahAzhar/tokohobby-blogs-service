package com.tokohobby.blogs.dto;
import lombok.Data;
@Data
public class CommentRequest {
    private Long blogId;
    private String content;
    private String userId; 
    private Long parentId;
}