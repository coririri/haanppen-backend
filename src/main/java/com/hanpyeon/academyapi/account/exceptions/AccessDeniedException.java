package com.hanpyeon.academyapi.account.exceptions;

import com.hanpyeon.academyapi.exception.ErrorCode;

public class AccessDeniedException extends AccountException{
    public AccessDeniedException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AccessDeniedException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
