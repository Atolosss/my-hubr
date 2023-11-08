package com.example.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    ERR_CODE_001("ERR.CODE.001", "Comment with id %s not found"),
    ERR_CODE_002("ERR.CODE.002", "Post with id %s not found");

    private final String code;
    private final String description;

    public String formatDescription(final Object... args) {
        return String.format(description, args);
    }
}
