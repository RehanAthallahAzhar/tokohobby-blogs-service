package com.tokohobby.blogs.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class) // Auto-populates createdAt/updatedAt
@SQLDelete(sql = "UPDATE comments SET deleted = true WHERE id = ?") // Soft delete
@SQLRestriction("deleted = false") // Filter out soft-deleted records by default
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private Long likes;

    @Column(nullable = false)
    private Long dislikes;

    @ManyToOne
    @JoinColumn(name = "blog_id")
    private Blog blog;

    @Column(nullable = false)
    private String userId;

    private String replyToUserId; // user id of the user who is being replied to

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent; // who is the parent of this comment

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private java.util.List<Comment> replies; // who are the children of this comment

    @Builder.Default
    private boolean deleted = false;
}