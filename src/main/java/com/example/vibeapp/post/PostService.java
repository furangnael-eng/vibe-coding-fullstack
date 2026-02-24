package com.example.vibeapp.post;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final PostTagRepository postTagRepository;

    public PostService(PostRepository postRepository, PostTagRepository postTagRepository) {
        this.postRepository = postRepository;
        this.postTagRepository = postTagRepository;
    }

    @Transactional
    public PostResponseDto findPostByNo(Long no) {
        postRepository.incrementViews(no);
        Post post = postRepository.findById(no);
        if (post != null) {
            List<PostTag> tagEntities = postTagRepository.findByPostNo(no);
            List<String> tagNames = tagEntities.stream().map(PostTag::getTagName).toList();
            post.setTags(tagNames);
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
        postRepository.save(post);

        saveTags(post.getNo(), createDto.tags());
    }

    @Transactional
    public void updatePost(Long no, PostUpdateDto updateDto) {
        Post post = postRepository.findById(no);
        if (post != null) {
            post.setTitle(updateDto.title());
            post.setContent(updateDto.content());
            post.setUpdatedAt(LocalDateTime.now());
            postRepository.save(post);

            postTagRepository.deleteByPostNo(no);
            saveTags(no, updateDto.tags());
        }
    }

    private void saveTags(Long postNo, String tagsString) {
        if (tagsString != null && !tagsString.isBlank()) {
            String[] tagArray = tagsString.split(",");
            for (String tagName : tagArray) {
                if (tagName != null && !tagName.trim().isBlank()) {
                    String trimmedName = tagName.trim();
                    if (trimmedName.length() > 50) {
                        throw new IllegalArgumentException("태그는 각각 50자 이내로 입력해야 합니다: " + trimmedName);
                    }
                    postTagRepository.insert(new PostTag(postNo, trimmedName));
                }
            }
        }
    }

    @Transactional
    public void deletePost(Long no) {
        postRepository.deleteById(no);
    }
}
