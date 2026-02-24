package com.example.vibeapp.post;

import java.time.LocalDateTime;

public record PostListDto(
        Long no,
        String title,
        LocalDateTime createdAt,
        Integer views) {
    public static PostListDto from(Post post) {
        return new PostListDto(
                post.getNo(),
                post.getTitle(),
                post.getCreatedAt(),
                post.getViews());
    }
}
