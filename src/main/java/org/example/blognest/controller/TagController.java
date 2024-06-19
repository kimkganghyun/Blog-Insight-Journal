package org.example.blognest.controller;

import lombok.RequiredArgsConstructor;
import org.example.blognest.model.Tag;
import org.example.blognest.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;

    // 새로운 태그 생성
    @PostMapping("/create")
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag) {
        Tag createdTag = tagService.create(tag);
        return ResponseEntity.ok(createdTag);
    }

    // 태그 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }

    // 모든 태그 목록 조회
    @GetMapping("/all")
    public ResponseEntity<List<Tag>> getAllTags() {
        List<Tag> tags = tagService.getAllTags();
        return ResponseEntity.ok(tags);
    }

    // 특정 포스트에 연결된 태그 목록 조회
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Tag>> getTagsByPost(@PathVariable Long postId) {
        List<Tag> tags = tagService.getTagsByPost(postId);
        return ResponseEntity.ok(tags);
    }
}
