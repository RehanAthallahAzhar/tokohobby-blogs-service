package com.tokohobby.blogs.repository;

import com.tokohobby.blogs.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByBlogIdOrderByCreatedAtDesc(Long blogId);
    List<Comment> findByBlogIdAndParentIsNullOrderByCreatedAtDesc(Long blogId);
}
