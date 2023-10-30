package com.example.exceptions;

public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 123456789L;

    public ServiceException(final ErrorCode errorCode, final Object... args) {
        super(errorCode.formatDescription(args));
    }

    public ServiceException(final ErrorCode errorCode) {
        super(errorCode.getDescription());
    }
}
