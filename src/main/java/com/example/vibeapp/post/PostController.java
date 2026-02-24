package com.example.vibeapp.post;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 목록 조회: GET /api/posts?page=1&size=5
    @GetMapping
    public ResponseEntity<Map<String, Object>> getPostList(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size) {
        List<PostListDto> posts = postService.findPagedPosts(page, size);
        int totalPages = postService.getTotalPages(size);

        Map<String, Object> response = new LinkedHashMap<>();
        response.put("posts", posts);
        response.put("currentPage", page);
        response.put("totalPages", totalPages);
        return ResponseEntity.ok(response);
    }

    // 게시글 상세 조회: GET /api/posts/{no}
    @GetMapping("/{no}")
    public ResponseEntity<PostResponseDto> getPostDetail(@PathVariable Long no) {
        PostResponseDto post = postService.findPostByNo(no);
        return ResponseEntity.ok(post);
    }

    // 새 게시글 등록: POST /api/posts
    @PostMapping
    public ResponseEntity<Void> addPost(@Valid @RequestBody PostCreateDto createDto) {
        postService.addPost(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 게시글 수정: PATCH /api/posts/{no}
    @PatchMapping("/{no}")
    public ResponseEntity<Void> updatePost(@PathVariable Long no,
            @Valid @RequestBody PostUpdateDto updateDto) {
        postService.updatePost(no, updateDto);
        return ResponseEntity.ok().build();
    }

    // 게시글 삭제: DELETE /api/posts/{no}
    @DeleteMapping("/{no}")
    public ResponseEntity<Void> deletePost(@PathVariable Long no) {
        postService.deletePost(no);
        return ResponseEntity.noContent().build();
    }
}
