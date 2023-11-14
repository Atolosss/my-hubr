package com.example.exceptions;

import com.example.constant.ErrorCode;
import lombok.AllArgsConstructor;

import java.io.Serial;

@AllArgsConstructor
public class ServiceException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 123456789L;

    public ServiceException(final ErrorCode errorCode, final Object... args) {
        super(errorCode.formatDescription(args));
    }

    public ServiceException(final ErrorCode errorCode) {
        super(errorCode.getDescription());
    }
}
