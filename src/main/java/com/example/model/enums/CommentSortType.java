package com.example.model.enums;

import com.example.model.entity.Comment;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;

@RequiredArgsConstructor
@Getter
public enum CommentSortType {
    COMMENT(Comparator.comparing(Comment::getComment)),
    ID(Comparator.comparingLong(Comment::getId));

    private final Comparator<Comment> comparator;
}
