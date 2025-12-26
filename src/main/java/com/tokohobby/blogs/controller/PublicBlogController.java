package com.tokohobby.blogs.controller;

import com.tokohobby.blogs.dto.BlogResponse;
import com.tokohobby.blogs.dto.PagedResponse;
import com.tokohobby.blogs.service.BlogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blogs")
@RequiredArgsConstructor
@Slf4j
public class PublicBlogController {

    private final BlogService blogService;

    @GetMapping
    public PagedResponse<BlogResponse> getBlogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String keyword) {
        log.info("REST request to get public blogs. page: {}, size: {}, keyword: {}", page, size, keyword);
        return blogService.getPublicBlogs(page, size, keyword);
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
}
