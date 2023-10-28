package com.example.model.enums;

import com.example.model.entity.Post;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;

@RequiredArgsConstructor
@Getter
public enum PostSortType {

    NAME(Comparator.comparing(Post::getName)),
    ID(Comparator.comparingLong(Post::getId));


    private final Comparator<Post> comparator;
    }
