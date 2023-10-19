package com.example.service;

import com.example.model.entity.Post;
import com.example.model.dto.RequestCreatePost;
import com.example.model.enums.PostSortType;
import jakarta.annotation.Nullable;

import java.util.List;

public interface PostService {
    List<Post> findAll(@Nullable final PostSortType sortType);

    Post findById(Long id);

    Post save(RequestCreatePost requestCreatePost);
}
