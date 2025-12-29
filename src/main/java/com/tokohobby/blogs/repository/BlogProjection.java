package com.tokohobby.blogs.repository;

import java.time.LocalDateTime;

public interface BlogProjection {
    Long getId();
    String getTitle();
    String getSlug();
    String getAuthorId();
    LocalDateTime getPublishedAt();
    String getContent();
    String getYoutubeLink();
    String getImagePath();
    String getCategoryName();
    String getTagNames();
}
