package com.tokohobby.blogs.controller;

import com.tokohobby.blogs.entity.Tag;
import com.tokohobby.blogs.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/tags")
@RequiredArgsConstructor
public class PublicTagController {
    private final TagService tagService;

    @GetMapping
    public List<Tag> getTags() {
        return tagService.getAllTags();
    }
}
