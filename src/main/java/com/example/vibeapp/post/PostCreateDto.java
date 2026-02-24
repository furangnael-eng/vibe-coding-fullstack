package com.example.vibeapp.post;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "게시글 등록 요청 DTO")
public record PostCreateDto(
        @Schema(description = "게시글 제목 (최대 100자)", example = "Spring Boot 시작하기") @NotBlank(message = "제목은 필수 입력 항목입니다.") @Size(max = 100, message = "제목은 100자 이내로 입력해주세요.") String title,

        @Schema(description = "게시글 내용", example = "Spring Boot는 설정을 최소화하여 빠르게 개발할 수 있게 해주는 프레임워크입니다.") @NotBlank(message = "내용은 필수 입력 항목입니다.") String content,

        @Schema(description = "태그 (쉼표로 구분, 각 태그 50자 이내)", example = "Java, Spring, Backend") String tags) {

    public Post toEntity() {
        return new Post(null, title, content, null, null, 0, null);
    }
}
