package com.example.exceptions;

import com.example.constant.ErrorCode;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ServiceException extends RuntimeException {

    public ServiceException(final ErrorCode errorCode, final Object... args) {
        super(errorCode.formatDescription(args));
    }

    public ServiceException(final ErrorCode errorCode) {
        super(errorCode.getDescription());
    }
}
