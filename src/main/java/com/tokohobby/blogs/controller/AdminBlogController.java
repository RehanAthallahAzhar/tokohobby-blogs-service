package com.tokohobby.blogs.controller;

import com.tokohobby.blogs.dto.BlogRequest;
import com.tokohobby.blogs.dto.BlogResponse;
import com.tokohobby.blogs.service.BlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/blogs")
@RequiredArgsConstructor
public class AdminBlogController {

    private final BlogService blogService;

    @GetMapping
    public com.tokohobby.blogs.dto.PagedResponse<BlogResponse> getBlogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return blogService.getAllBlogs(page, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BlogResponse createBlog(@Valid @RequestBody BlogRequest request) {
        return blogService.createBlog(request);
    }

    @PutMapping("/{id}")
    public BlogResponse updateBlog(@PathVariable Long id, @Valid @RequestBody BlogRequest request) {
        return blogService.updateBlog(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBlog(@PathVariable Long id) {
        blogService.deleteBlog(id);
    }
}
