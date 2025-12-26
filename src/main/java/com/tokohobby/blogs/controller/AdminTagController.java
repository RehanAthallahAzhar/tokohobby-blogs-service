package com.tokohobby.blogs.controller;

import com.tokohobby.blogs.dto.TagRequest;
import com.tokohobby.blogs.entity.Tag;
import com.tokohobby.blogs.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/tags")
@RequiredArgsConstructor
@Slf4j
public class AdminTagController {

    private final TagService tagService;

    @GetMapping
    public List<Tag> getTags() {
        return tagService.getAllTags();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tag createTag(@Valid @RequestBody TagRequest request) {
        log.info("REST request to create tag: {}", request.getName());
        return tagService.createTag(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable Long id) {
        log.warn("REST request to delete tag with id: {}", id);
        tagService.deleteTag(id);
    }
}
