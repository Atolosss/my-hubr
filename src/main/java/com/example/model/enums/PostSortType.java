package com.example.model.enums;

import com.example.model.entity.PostToChat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;

@RequiredArgsConstructor
@Getter
public enum PostSortType {

    NAME(Comparator.comparing(PostToChat::getName)),

    ID(Comparator.comparingLong(PostToChat::getId));

    private final Comparator<PostToChat> comparator;
}
