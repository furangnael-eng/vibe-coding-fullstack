package com.example.vibeapp.post;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "게시글 상세 조회 응답 DTO")
public record PostResponseDto(
        @Schema(description = "게시글 번호", example = "1") Long no,
        @Schema(description = "게시글 제목", example = "Spring Boot 시작하기") String title,
        @Schema(description = "게시글 내용", example = "Spring Boot는...") String content,
        @Schema(description = "작성일시") LocalDateTime createdAt,
        @Schema(description = "수정일시") LocalDateTime updatedAt,
        @Schema(description = "조회수", example = "42") Integer views,
        @Schema(description = "태그 목록", example = "[\"Java\", \"Spring\"]") List<String> tags) {

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
