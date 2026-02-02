package com.tokohobby.blogs.messaging.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogPublishedEvent {
    private String type = "blog.published";
    private String blogId;
    private String authorId;
    private String authorName;
    private String title;
    private String excerpt;
    private LocalDateTime publishedAt;
}
