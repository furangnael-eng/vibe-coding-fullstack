package com.example.vibeapp.post;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("VibeApp API")
                        .description("VibeApp 게시판 REST API 문서")
                        .version("v1.0.0")
                        .contact(new Contact().name("VibeApp Team")));
    }

    // 공통 에러 응답을 모든 오퍼레이션에 자동 추가
    @Bean
    public GlobalOpenApiCustomizer globalApiCustomizer() {
        return openApi -> {
            if (openApi.getPaths() == null)
                return;
            openApi.getPaths().forEach((path, pathItem) -> pathItem.readOperations().forEach(operation -> {
                operation.getResponses().addApiResponse("400",
                        new ApiResponse().description("잘못된 요청 (유효성 검사 실패 또는 비즈니스 규칙 위반)"));
                operation.getResponses().addApiResponse("500",
                        new ApiResponse().description("서버 내부 오류"));
            }));
        };
    }
}
