package com.example.vibeapp.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostUpdateDto(
        @NotBlank(message = "제목은 필수 입력 항목입니다.") @Size(max = 100, message = "제목은 100자 이내로 입력해주세요.") String title,

        @NotBlank(message = "내용은 필수 입력 항목입니다.") String content,
        String tags) {
}
