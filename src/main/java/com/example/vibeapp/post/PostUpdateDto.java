package com.example.vibeapp.post;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "게시글 수정 요청 DTO")
public record PostUpdateDto(
                @Schema(description = "게시글 제목 (최대 100자)", example = "Spring Boot 시작하기 (수정)") @NotBlank(message = "제목은 필수 입력 항목입니다.") @Size(max = 100, message = "제목은 100자 이내로 입력해주세요.") String title,

                @Schema(description = "게시글 내용", example = "수정된 내용입니다.") @NotBlank(message = "내용은 필수 입력 항목입니다.") String content,

                @Schema(description = "태그 (쉼표로 구분, 각 태그 50자 이내)", example = "Java, JPA") String tags) {
}
