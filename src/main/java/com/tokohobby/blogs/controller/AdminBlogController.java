package com.tokohobby.blogs.controller;

import com.tokohobby.blogs.dto.BlogRequest;
import com.tokohobby.blogs.dto.BlogResponse;
import com.tokohobby.blogs.service.BlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/blogs")
@RequiredArgsConstructor
@Slf4j
public class AdminBlogController {

    private final BlogService blogService;

    // real case : would have @PreAuthorize or similar security here
    // For this simple example, assume the existence of a simple header or similar

    @GetMapping
    public com.tokohobby.blogs.dto.PagedResponse<BlogResponse> getBlogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return blogService.getAllBlogs(page, size);
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
