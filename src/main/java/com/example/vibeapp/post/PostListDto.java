package com.example.vibeapp.post;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "게시글 목록 조회 응답 DTO")
public record PostListDto(
        @Schema(description = "게시글 번호", example = "1") Long no,
        @Schema(description = "게시글 제목", example = "Spring Boot 시작하기") String title,
        @Schema(description = "작성일시") LocalDateTime createdAt,
        @Schema(description = "조회수", example = "42") Integer views) {

    public static PostListDto from(Post post) {
        return new PostListDto(
                post.getNo(),
                post.getTitle(),
                post.getCreatedAt(),
                post.getViews());
    }
}
