package com.example.service;

import com.example.model.entity.Post;
import com.example.model.dto.RequestCreatePost;
import com.example.model.enums.PostSortType;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Service
public class PostServiceOriginalImpl implements PostService {
    private static final List<Post> POST_LIST = new LinkedList<>();

    @Override
    public List<Post> findAll(@Nullable final PostSortType sortType) {
        if (sortType == null) {
            return POST_LIST;
        }

        return POST_LIST.stream()
            .sorted(sortType.getComparator())
            .toList();
    }

    @Override
    public Post findById(final Long id) {
        return POST_LIST.stream()
            .filter(post -> post.getId().equals(id))
            .findAny()
            .orElseThrow();
    }

    @Override
    public Post save(final RequestCreatePost requestCreatePost) {
        Post post = Post.builder()
            .id(new Random().nextLong())
            .name(requestCreatePost.getName() + " original")
            .description(requestCreatePost.getDescription() + " original")
            .build();
        POST_LIST.add(post);
        return post;
    }
}
