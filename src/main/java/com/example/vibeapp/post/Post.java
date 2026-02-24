package com.example.vibeapp.post;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "POSTS")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NO")
    private Long no;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "CLOB")
    private String content;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @Column(defaultValue = "0")
    private Integer views = 0;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostTag> tags = new ArrayList<>();

    public Post() {
    }

    public Post(Long no, String title, String content, LocalDateTime createdAt, LocalDateTime updatedAt,
            Integer views, List<PostTag> tags) {
        this.no = no;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.views = views;
        this.tags = tags;
    }

    // Getters
    public Long getNo() {
        return no;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Integer getViews() {
        return views;
    }

    public List<PostTag> getTags() {
        return tags;
    }

    // Setters
    public void setNo(Long no) {
        this.no = no;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public void setTags(List<PostTag> tags) {
        this.tags = tags;
    }

    // 편의 메서드
    public void addTag(String tagName) {
        PostTag postTag = new PostTag(this, tagName);
        this.tags.add(postTag);
    }
}
