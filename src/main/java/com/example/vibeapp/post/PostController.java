package com.example.vibeapp.post;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "Post", description = "게시글 CRUD API")
@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(summary = "게시글 목록 조회", description = "페이지 번호와 페이지 크기를 기준으로 게시글 목록을 반환합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = Map.class)))
    })
    @GetMapping
    public ResponseEntity<Map<String, Object>> getPostList(
            @Parameter(description = "페이지 번호 (1부터 시작)", example = "1") @RequestParam(value = "page", defaultValue = "1") int page,
            @Parameter(description = "페이지 당 게시글 수", example = "5") @RequestParam(value = "size", defaultValue = "5") int size) {
        List<PostListDto> posts = postService.findPagedPosts(page, size);
        int totalPages = postService.getTotalPages(size);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("posts", posts);
        response.put("currentPage", page);
        response.put("totalPages", totalPages);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "게시글 상세 조회", description = "게시글 번호(no)로 게시글 상세 정보를 반환합니다. 조회 시 조회수가 1 증가합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공", content = @Content(schema = @Schema(implementation = PostResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음", content = @Content(schema = @Schema(example = "{\"error\": \"게시글을 찾을 수 없습니다.\"}")))
    })
    @GetMapping("/{no}")
    public ResponseEntity<PostResponseDto> getPostDetail(
            @Parameter(description = "게시글 번호", example = "1", required = true) @PathVariable Long no) {
        PostResponseDto post = postService.findPostByNo(no);
        return ResponseEntity.ok(post);
    }

    @Operation(summary = "새 게시글 등록", description = "새 게시글을 등록합니다. 태그는 쉼표(,)로 구분하여 입력합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "등록 성공"),
            @ApiResponse(responseCode = "400", description = "유효성 검사 실패 (제목 누락, 태그 50자 초과 등)", content = @Content(schema = @Schema(example = "{\"error\": \"제목은 필수 입력 항목입니다.\"}")))
    })
    @PostMapping
    public ResponseEntity<Void> addPost(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "게시글 등록 정보", required = true, content = @Content(schema = @Schema(implementation = PostCreateDto.class))) @Valid @RequestBody PostCreateDto createDto) {
        postService.addPost(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "게시글 수정", description = "기존 게시글의 제목, 내용, 태그를 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공"),
            @ApiResponse(responseCode = "400", description = "유효성 검사 실패", content = @Content(schema = @Schema(example = "{\"error\": \"태그는 50자 이내로 입력해주세요.\"}"))),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음", content = @Content(schema = @Schema(example = "{\"error\": \"게시글을 찾을 수 없습니다.\"}")))
    })
    @PatchMapping("/{no}")
    public ResponseEntity<Void> updatePost(
            @Parameter(description = "게시글 번호", example = "1", required = true) @PathVariable Long no,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "게시글 수정 정보", required = true, content = @Content(schema = @Schema(implementation = PostUpdateDto.class))) @Valid @RequestBody PostUpdateDto updateDto) {
        postService.updatePost(no, updateDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "게시글 삭제", description = "게시글 번호(no)에 해당하는 게시글과 관련 태그를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공"),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음", content = @Content(schema = @Schema(example = "{\"error\": \"게시글을 찾을 수 없습니다.\"}")))
    })
    @DeleteMapping("/{no}")
    public ResponseEntity<Void> deletePost(
            @Parameter(description = "게시글 번호", example = "1", required = true) @PathVariable Long no) {
        postService.deletePost(no);
        return ResponseEntity.noContent().build();
    }
}
