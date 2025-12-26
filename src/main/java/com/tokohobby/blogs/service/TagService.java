package com.tokohobby.blogs.service;

import com.tokohobby.blogs.dto.TagRequest;
import com.tokohobby.blogs.entity.Tag;
import com.tokohobby.blogs.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class TagService {

    private final TagRepository tagRepository;

    @Transactional(readOnly = true)
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    @Transactional
    public Tag createTag(TagRequest request) {
        log.info("Creating new tag: {}", request.getName());
        
        String slug = request.getName().toLowerCase().replaceAll("\\s+", "-");
        
        Tag tag = Tag.builder()
                .name(request.getName())
                .slug(slug)
                .build();
        
        return tagRepository.save(tag);
    }

    @Transactional
    public void deleteTag(Long id) {
        log.warn("Deleting tag with id: {}", id);
        if (!tagRepository.existsById(id)) {
            throw new NoSuchElementException("Tag not found with id: " + id);
        }
        tagRepository.deleteById(id);
    }
}
