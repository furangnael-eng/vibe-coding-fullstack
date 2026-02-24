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

    public List<Post> getPosts() {
        return postRepository.findAll();
    }

    public Post getPost(Long no) {
        return postRepository.findById(no);
    }

    public List<Post> getPagedPosts(int page, int size) {
        int offset = (page - 1) * size;
        return postRepository.findPaged(offset, size);
    }

    public int getTotalPages(int size) {
        int totalPosts = postRepository.countAll();
        return (int) Math.ceil((double) totalPosts / size);
    }

    public void addPost(String title, String content) {
        Post post = new Post(
                null,
                title,
                content,
                LocalDateTime.now(),
                null,
                0);
        postRepository.save(post);
    }

    public void updatePost(Long no, String title, String content) {
        Post post = postRepository.findById(no);
        if (post != null) {
            post.setTitle(title);
            post.setContent(content);
            post.setUpdatedAt(LocalDateTime.now());
        }
    }

    public void deletePost(Long no) {
        postRepository.deleteById(no);
    }
}
