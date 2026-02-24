package com.example.vibeapp.post;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostRepository {

    void save(Post post);

    Post findById(Long no);

    void deleteById(Long no);

    int countAll();

    List<Post> findPaged(@Param("offset") int offset, @Param("limit") int limit);
}
