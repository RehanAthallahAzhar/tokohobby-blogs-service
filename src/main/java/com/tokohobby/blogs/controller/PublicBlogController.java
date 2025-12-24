package com.tokohobby.blogs.controller;

import com.tokohobby.blogs.dto.BlogResponse;
import com.tokohobby.blogs.dto.PagedResponse;
import com.tokohobby.blogs.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blogs")
@RequiredArgsConstructor
public class PublicBlogController {

    private final BlogService blogService;

    @GetMapping
    public PagedResponse<BlogResponse> getBlogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword) {
        return blogService.getPublicBlogs(page, size, keyword);
    }

    @GetMapping("/{slug}")
    public BlogResponse getBlogBySlug(@PathVariable String slug) {
        return blogService.getPublicBlogBySlug(slug);
    }
}
