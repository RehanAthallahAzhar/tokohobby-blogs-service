package com.tokohobby.blogs.controller;

import com.tokohobby.blogs.dto.BlogRequest;
import com.tokohobby.blogs.dto.BlogResponse;
import com.tokohobby.blogs.dto.PagedResponse;
import com.tokohobby.blogs.service.BlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/blogs")
@RequiredArgsConstructor
@Slf4j
public class PublicBlogController {

    private final BlogService blogService;

    @GetMapping
    public PagedResponse<BlogResponse> getBlogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId) {
        log.info("REST request to get public blogs. page: {}, size: {}, keyword: {}, categoryId: {}", page, size, keyword, categoryId);
        return blogService.getPublicBlogs(page, size, keyword, categoryId);
    }

    @GetMapping("/manage")
    public PagedResponse<BlogResponse> getAllBlogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("REST request to get all blogs for management");
        return blogService.getAllBlogs(page, size);
    }

    @GetMapping("/{slug}")
    public BlogResponse getBlogBySlug(@PathVariable String slug) {
        log.info("REST request to get public blog by slug: {}", slug);
        return blogService.getPublicBlogBySlug(slug);
    }

    @GetMapping("/author/{authorId}")
    public BlogResponse getBlogByAuthorId(@PathVariable String authorId) {
        return blogService.getPublicBlogByAuthorId(authorId);
    }

    @GetMapping("/preview/{token}")
    public BlogResponse getBlogByPreviewToken(@PathVariable String token) {
        log.info("REST request to preview blog with token: {}", token);
        return blogService.getBlogByPreviewToken(token);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BlogResponse createBlog(@Valid @RequestBody BlogRequest request) {
        log.info("REST request to create blog: {}", request.getTitle());
        return blogService.createBlog(request);
    }

    @PutMapping("/{id}")
    public BlogResponse updateBlog(@PathVariable Long id, @Valid @RequestBody BlogRequest request) {
        log.info("REST request to update blog with id: {}", id);
        return blogService.updateBlog(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBlog(@PathVariable Long id) {
        log.warn("REST request to delete blog with id: {}", id);
        blogService.deleteBlog(id);
    }
}
