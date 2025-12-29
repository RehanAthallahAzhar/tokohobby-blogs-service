package com.tokohobby.blogs.repository;

import com.tokohobby.blogs.entity.Blog;
import com.tokohobby.blogs.entity.BlogStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {

    // Find published blogs, filtering by title if keyword is provided
    Page<Blog> findByStatusAndTitleContainingIgnoreCase(BlogStatus BlogStatus, String title, Pageable pageable);

    // Find a specific published blog by slug
    Optional<Blog> findBySlugAndStatus(String slug, BlogStatus status);

    // Find a specific published blog by author id
    Optional<Blog> findByAuthorIdAndStatus(String authorId, BlogStatus status);

    // Find blog by preview token
    Optional<Blog> findByPreviewToken(String previewToken);

    // Check if slug exists
    boolean existsBySlug(String slug);

    // Optimized Hot Path: Fetch only required columns for public blog list using Native Query
    @Query(value = "SELECT b.id, b.title, b.slug, b.author_id as authorId, b.published_at as publishedAt, b.content, b.youtube_link as youtubeLink, b.image_path as imagePath, c.name as categoryName, " +
                   "STRING_AGG(t.name, ',') as tagNames " +
                   "FROM blogs b " +
                   "LEFT JOIN categories c ON b.category_id = c.id " +
                   "LEFT JOIN blog_tags bt ON b.id = bt.blog_id " +
                   "LEFT JOIN tags t ON bt.tag_id = t.id " +
                   "WHERE b.status = 'PUBLISHED' " +
                   "AND b.deleted = false " +
                   "AND (LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
                   "AND (:categoryId IS NULL OR b.category_id = :categoryId) " +
                   "GROUP BY b.id, c.name " +
                   "ORDER BY b.published_at DESC",
           countQuery = "SELECT count(*) FROM blogs b WHERE b.status = 'PUBLISHED' AND b.deleted = false AND LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) AND (:categoryId IS NULL OR b.category_id = :categoryId)",
           nativeQuery = true)
    Page<BlogProjection> findPublicBlogsOptimized(@Param("keyword") String keyword, @Param("categoryId") Long categoryId, Pageable pageable);
}