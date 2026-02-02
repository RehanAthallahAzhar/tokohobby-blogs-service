package com.tokohobby.blogs.messaging;

import com.tokohobby.blogs.config.RabbitMQConfig;
import com.tokohobby.blogs.messaging.events.BlogPublishedEvent;
import com.tokohobby.blogs.messaging.events.CommentAddedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BlogEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishBlogPublished(BlogPublishedEvent event) {
        try {
            rabbitTemplate.convertAndSend(
                RabbitMQConfig.BLOG_EXCHANGE,
                RabbitMQConfig.BLOG_PUBLISHED_KEY,
                event
            );
            log.info("Published blog.published event for blog: {}", event.getBlogId());
        } catch (Exception e) {
            log.error("Failed to publish blog published event: {}", e.getMessage(), e);
        }
    }

    public void publishCommentAdded(CommentAddedEvent event) {
        try {
            rabbitTemplate.convertAndSend(
                RabbitMQConfig.BLOG_EXCHANGE,
                RabbitMQConfig.COMMENT_ADDED_KEY,
                event
            );
            log.info("Published comment.added event for blog: {}", event.getBlogId());
        } catch (Exception e) {
            log.error("Failed to publish comment added event: {}", e.getMessage(), e);
        }
    }
}
