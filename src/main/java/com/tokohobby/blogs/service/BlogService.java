package com.tokohobby.blogs.service;

import com.tokohobby.blogs.dto.BlogRequest;
import com.tokohobby.blogs.dto.BlogResponse;
import com.tokohobby.blogs.dto.PagedResponse;
import com.tokohobby.blogs.entity.Blog;
import com.tokohobby.blogs.entity.BlogStatus;
import com.tokohobby.blogs.entity.Category;
import com.tokohobby.blogs.entity.Tag;
import com.tokohobby.blogs.repository.BlogProjection;
import com.tokohobby.blogs.repository.BlogRepository;
import com.tokohobby.blogs.repository.CategoryRepository;
import com.tokohobby.blogs.repository.TagRepository;
import com.tokohobby.blogs.security.UserContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlogService {

    private final BlogRepository blogRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    // Public: Get published blogs
    @Transactional(readOnly = true)
    public PagedResponse<BlogResponse> getPublicBlogs(int page, int size, String keyword) {
        Pageable pageable = PageRequest.of(page, size); // Sorting is handled by Native Query
        
        Page<BlogProjection> blogPage = blogRepository.findPublicBlogsOptimized(
                keyword != null ? keyword : "", 
                pageable
        );

        List<BlogResponse> content = blogPage.getContent().stream()
                .map(this::mapProjectionToDTO)
                .collect(Collectors.toList());

        return PagedResponse.<BlogResponse>builder()
                .content(content)
                .pageNumber(blogPage.getNumber())
                .pageSize(blogPage.getSize())
                .totalElements(blogPage.getTotalElements())
                .totalPages(blogPage.getTotalPages())
                .last(blogPage.isLast())
                .build();
    }

    // Public: Get single published blog by slug
    @Transactional(readOnly = true)
    public BlogResponse getPublicBlogBySlug(String slug) {
        Blog blog = blogRepository.findBySlugAndStatus(slug, BlogStatus.PUBLISHED)
                .orElseThrow(() -> new NoSuchElementException("Blog not found: " + slug));
        return mapToDTO(blog);
    }

    // Public: Get single published blog by author id
    @Transactional(readOnly = true)
    public BlogResponse getPublicBlogByAuthorId(String authorId) {
        Blog blog = blogRepository.findByAuthorIdAndStatus(authorId, BlogStatus.PUBLISHED)
                .orElseThrow(() -> new NoSuchElementException("Blog not found: " + authorId));
        return mapToDTO(blog);
    }

    // Public: Get blog by preview token
    @Transactional(readOnly = true)
    public BlogResponse getBlogByPreviewToken(String token) {
        Blog blog = blogRepository.findByPreviewToken(token)
                .orElseThrow(() -> new NoSuchElementException("Preview link invalid or expired"));
        return mapToDTO(blog);
    }

    // Admin: Get all blogs (for admin panel)
    @Transactional(readOnly = true)
    public PagedResponse<BlogResponse> getAllBlogs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Blog> blogPage = blogRepository.findAll(pageable);
        return mapToPagedResponse(blogPage);
    }

    // Admin/Writer: Create new blog
    @Transactional
    public BlogResponse createBlog(BlogRequest request) {
        log.info("Creating new blog with title: {}", request.getTitle());
        String slug = generateSlug(request.getTitle());
        String authorId = UserContext.getUser().getUserId();
        
        Blog.BlogBuilder builder = Blog.builder()
                .authorId(authorId)
                .title(request.getTitle())
                .slug(slug)
                .content(request.getContent())
                .status(request.getStatus())
                .youtubeLink(request.getYoutubeLink())
                .imagePath(request.getImagePath())
                .publishedAt(request.getStatus() == BlogStatus.PUBLISHED ? LocalDateTime.now() : null);

        if (request.getStatus() == BlogStatus.DRAFT) {
            builder.previewToken(UUID.randomUUID().toString());
        }

        if (request.getCategoryId() != null) {
            builder.category(categoryRepository.findById(request.getCategoryId()).orElse(null));
        }

        if (request.getTagIds() != null && !request.getTagIds().isEmpty()) {
            builder.tags(new HashSet<>(tagRepository.findAllById(request.getTagIds())));
        }

        return mapToDTO(blogRepository.save(builder.build()));
    }

    // Admin/Owner: Update blog
    @Transactional
    public BlogResponse updateBlog(Long id, BlogRequest request) {
        log.info("Updating blog with id: {}", id);
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Blog not found with id: " + id));

        verifyOwnershipOrAdmin(blog);

        blog.setTitle(request.getTitle());
        blog.setContent(request.getContent());
        
        if (blog.getStatus() != BlogStatus.PUBLISHED && request.getStatus() == BlogStatus.PUBLISHED) {
            blog.setPublishedAt(LocalDateTime.now());
            blog.setPreviewToken(null); // Clear token when published
        } else if (request.getStatus() == BlogStatus.DRAFT && blog.getPreviewToken() == null) {
            blog.setPreviewToken(UUID.randomUUID().toString());
        }
        
        blog.setStatus(request.getStatus());
        blog.setYoutubeLink(request.getYoutubeLink());
        blog.setImagePath(request.getImagePath());

        if (request.getCategoryId() != null) {
            blog.setCategory(categoryRepository.findById(request.getCategoryId()).orElse(null));
        }

        if (request.getTagIds() != null) {
            blog.setTags(new HashSet<>(tagRepository.findAllById(request.getTagIds())));
        }
        
        return mapToDTO(blogRepository.save(blog));
    }

    // Admin/Owner: Soft delete blog
    @Transactional
    public void deleteBlog(Long id) {
        log.warn("Deleting blog with id: {}", id);
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Blog not found with id: " + id));

        verifyOwnershipOrAdmin(blog);
        
        blogRepository.deleteById(id);
    }

    private void verifyOwnershipOrAdmin(Blog blog) {
        UserContext.User currentUser = UserContext.getUser();
        if ("admin".equalsIgnoreCase(currentUser.getRole())) {
            return;
        }

        if (!blog.getAuthorId().equals(currentUser.getUserId())) {
            throw new AccessDeniedException("You are not authorized to modify this blog");
        }
    }

    // Helper: Map Entity to DTO
    private BlogResponse mapToDTO(Blog blog) {
        BlogResponse.BlogResponseBuilder builder = BlogResponse.builder()
                .id(blog.getId())
                .authorId(blog.getAuthorId())
                .title(blog.getTitle())
                .slug(blog.getSlug())
                .content(blog.getContent())
                .status(blog.getStatus())
                .publishedAt(blog.getPublishedAt())
                .createdAt(blog.getCreatedAt())
                .updatedAt(blog.getUpdatedAt())
                .youtubeLink(blog.getYoutubeLink())
                .imagePath(blog.getImagePath())
                .previewToken(blog.getPreviewToken());

        if (blog.getCategory() != null) {
            builder.categoryName(blog.getCategory().getName());
        }

        if (blog.getTags() != null) {
            builder.tags(blog.getTags().stream().map(Tag::getName).collect(Collectors.toList()));
        }

        return builder.build();
    }

    // Helper: Map Projection to DTO (Optimized for List Path)
    private BlogResponse mapProjectionToDTO(BlogProjection projection) {
        return BlogResponse.builder()
                .id(projection.getId())
                .title(projection.getTitle())
                .slug(projection.getSlug())
                .publishedAt(projection.getPublishedAt())
                .content(projection.getContent())
                .youtubeLink(projection.getYoutubeLink())
                .imagePath(projection.getImagePath())
                .build();
    }

    // Helper: Map Page to PagedResponse
    private PagedResponse<BlogResponse> mapToPagedResponse(Page<Blog> page) {
        List<BlogResponse> content = page.getContent().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

        return PagedResponse.<BlogResponse>builder()
                .content(content)
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .last(page.isLast())
                .build();
    }

    // Helper: Generate unique slug
    private String generateSlug(String title) {
        String baseSlug = title.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "") // Remove invalid chars
                .replaceAll("\\s+", "-");        // Replace spaces with hyphens
        
        String slug = baseSlug;
        int count = 1;
        
        while (blogRepository.existsBySlug(slug)) {
            slug = baseSlug + "-" + count;
            count++;
        }
        
        return slug;
    }
}
