package com.tokohobby.blogs.dto;

import com.tokohobby.blogs.entity.BlogStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BlogRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Content is required")
    private String content;

    @NotNull(message = "Status is required")
    private BlogStatus status;

    private String youtubeLink;
    private String imagePath;

    private Long categoryId;
    private java.util.List<Long> tagIds;
}
