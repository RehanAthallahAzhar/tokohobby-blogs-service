package com.tokohobby.blogs.repository;

import com.tokohobby.blogs.entity.CommentEngagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentEngagementRepository extends JpaRepository<CommentEngagement, Long> {
    boolean existsByUserIdAndCommentId(String userId, Long commentId);
    Optional<CommentEngagement> findByUserIdAndCommentId(String userId, Long commentId);
}
