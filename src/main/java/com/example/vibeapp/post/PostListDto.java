package com.example.vibeapp.post;

import java.time.LocalDateTime;

public class PostListDto {
    private Long no;
    private String title;
    private LocalDateTime createdAt;
    private Integer views;

    public PostListDto(Long no, String title, LocalDateTime createdAt, Integer views) {
        this.no = no;
        this.title = title;
        this.createdAt = createdAt;
        this.views = views;
    }

    public static PostListDto from(Post post) {
        return new PostListDto(
                post.getNo(),
                post.getTitle(),
                post.getCreatedAt(),
                post.getViews());
    }

    // Getters
    public Long getNo() {
        return no;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Integer getViews() {
        return views;
    }
}
