package com.example.vibeapp.repository;

import com.example.vibeapp.domain.Post;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostRepository {
    private static final List<Post> posts = new ArrayList<>();

    static {
        for (int i = 1; i <= 10; i++) {
            posts.add(new Post(
                    (long) i,
                    "게시글 제목 " + i,
                    "게시글 내용 상세 " + i,
                    LocalDateTime.now().minusDays(10 - i),
                    LocalDateTime.now().minusDays(10 - i),
                    i * 10));
        }
    }

    public List<Post> findAll() {
        return new ArrayList<>(posts);
    }

    public Post findById(Long no) {
        return posts.stream()
                .filter(post -> post.getNo().equals(no))
                .findFirst()
                .orElse(null);
    }
}
