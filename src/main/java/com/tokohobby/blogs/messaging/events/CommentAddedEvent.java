package com.tokohobby.blogs.messaging.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentAddedEvent {
    private String type = "comment.added";
    private String commentId;
    private String blogId;
    private String blogTitle;
    private String commenterId;
    private String commenter;
    private String comment;
    private String blogOwnerId;
    private LocalDateTime createdAt;
}
