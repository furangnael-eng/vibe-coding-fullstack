package com.example.vibeapp.post;

import java.time.LocalDateTime;
import java.util.List;

public record PostResponseDto(
        Long no,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Integer views,
        List<String> tags) {
    public static PostResponseDto from(Post post) {
        if (post == null)
            return null;

        List<String> tagNames = post.getTags().stream()
                .map(PostTag::getTagName)
                .toList();

        return new PostResponseDto(
                post.getNo(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                post.getViews(),
                tagNames);
    }
}
