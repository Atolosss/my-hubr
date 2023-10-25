package com.example.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
    ERROR_CODE_0001("ERROR.CODE.0001", "Post not found exception with id %s"),
    ERROR_CODE_0002("ERROR.CODE.0002", "Comment not found exception with id %s");

    private final String code;
    private final String description;

    public String formatDescription(final Object... args) {
        return String.format(description, args);
    }
}
