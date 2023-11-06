package com.example.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;

@Data
@AllArgsConstructor
public class ServiceException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 123456789L;

    public ServiceException(final String message) {
        super(message);
    }
}
