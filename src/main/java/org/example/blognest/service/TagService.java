package org.example.blognest.service;

import lombok.RequiredArgsConstructor;
import org.example.blognest.model.Tag;
import org.example.blognest.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    // 태그 추가
    public Tag create(Tag tag) {
        return tagRepository.save(tag);
    }

    // 태그 삭제
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    // 모든 태그 조화
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    // 특정 포스트에 연결된 태그 목록 조회
    public List<Tag> getTagsByPost(Long postId) {
        return tagRepository.findTagsByPostId(postId);
    }
}
