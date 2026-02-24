package com.example.vibeapp.post;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public PostResponseDto findPostByNo(Long no) {
        postRepository.incrementViews(no);
        Post post = postRepository.findById(no);
        if (post == null) {
            throw new NoSuchElementException("게시글을 찾을 수 없습니다. (no=" + no + ")");
        }
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

    @Transactional
    public void addPost(PostCreateDto createDto) {
        Post post = createDto.toEntity();
        post.setCreatedAt(LocalDateTime.now());

        // 태그 처리: 가공된 태그 문자열을 엔티티 리스트로 변환하여 추가 (CascadeType.ALL에 의해 함께 저장됨)
        processAndSetTags(post, createDto.tags());

        postRepository.save(post);
    }

    @Transactional
    public void updatePost(Long no, PostUpdateDto updateDto) {
        Post post = postRepository.findById(no);
        if (post != null) {
            // 변경 감지(Dirty Checking): 영속성 컨텍스트가 관리하는 엔티티의 상태 변경을 감지하여 자동으로 UPDATE SQL 실행
            post.setTitle(updateDto.title());
            post.setContent(updateDto.content());
            post.setUpdatedAt(LocalDateTime.now());

            // 기존 태그 삭제 및 새 태그 추가 (orphanRemoval = true 설정으로 인해 리스트에서 제거 시 DB에서도 삭제됨)
            post.getTags().clear();
            processAndSetTags(post, updateDto.tags());
        }
    }

    private void processAndSetTags(Post post, String tagsString) {
        if (tagsString != null && !tagsString.isBlank()) {
            String[] tagArray = tagsString.split(",");
            for (String tagName : tagArray) {
                if (tagName != null && !tagName.trim().isBlank()) {
                    String trimmedName = tagName.trim();
                    if (trimmedName.length() > 50) {
                        throw new IllegalArgumentException("태그는 50자 이내로 입력해주세요.");
                    }
                    post.addTag(trimmedName);
                }
            }
        }
    }

    @Transactional
    public void deletePost(Long no) {
        // em.remove() 호출 시 연관된 PostTag들도 CascadeType.ALL에 의해 자동 삭제됨
        postRepository.deleteById(no);
    }
}
