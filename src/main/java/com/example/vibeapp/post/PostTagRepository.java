package com.example.vibeapp.post;

import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface PostTagRepository {
    void insert(PostTag postTag);

    List<PostTag> findByPostNo(Long postNo);

    void deleteByPostNo(Long postNo);
}
