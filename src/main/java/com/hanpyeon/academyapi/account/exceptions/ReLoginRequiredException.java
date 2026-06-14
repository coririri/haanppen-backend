package com.hanpyeon.academyapi.account.exceptions;

import com.hanpyeon.academyapi.exception.ErrorCode;

public class ReLoginRequiredException extends AccountException {
    public ReLoginRequiredException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ReLoginRequiredException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
