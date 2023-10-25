package com.example.exceptions;

public class ServiceException extends RuntimeException {
    public ServiceException(final ErrorCode errorCode, final Object... args) {
        super(errorCode.formatDescription(args));
    }

    public ServiceException(final ErrorCode errorCode) {
        super(errorCode.getDescription());
    }
}
