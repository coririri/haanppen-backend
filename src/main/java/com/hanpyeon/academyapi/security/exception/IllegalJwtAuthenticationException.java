package com.hanpyeon.academyapi.security.exception;

import org.springframework.security.core.AuthenticationException;

public class IllegalJwtAuthenticationException extends AuthenticationException {
    public IllegalJwtAuthenticationException(String msg) {
        super(msg);
    }
}
