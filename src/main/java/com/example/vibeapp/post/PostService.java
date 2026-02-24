package com.example.vibeapp.post;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostResponseDto findPostByNo(Long no) {
        Post post = postRepository.findById(no);
        return PostResponseDto.from(post);
    }

    public List<PostListDto> findPagedPosts(int page, int size) {
        int offset = (page - 1) * size;
        List<Post> posts = postRepository.findPaged(offset, size);
        return posts.stream()
                .map(PostListDto::from)
                .toList();
    }

    public int getTotalPages(int size) {
        int totalPosts = postRepository.countAll();
        return (int) Math.ceil((double) totalPosts / size);
    }

    public void addPost(PostCreateDto createDto) {
        Post post = createDto.toEntity();
        post.setCreatedAt(LocalDateTime.now());
        postRepository.save(post);
    }

    public void updatePost(Long no, PostUpdateDto updateDto) {
        Post post = postRepository.findById(no);
        if (post != null) {
            post.setTitle(updateDto.title());
            post.setContent(updateDto.content());
            post.setUpdatedAt(LocalDateTime.now());
            postRepository.save(post); // MyBatis 연동 시 dirty checking이 안되므로 명시적 호출 (save를 insert/update 겸용으로 보거나 update
                                       // 추가 필요)
        }
    }

    public void deletePost(Long no) {
        postRepository.deleteById(no);
    }
}
