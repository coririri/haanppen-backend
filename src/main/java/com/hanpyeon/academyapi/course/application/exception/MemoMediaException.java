package com.hanpyeon.academyapi.course.application.exception;

import com.hanpyeon.academyapi.exception.BusinessException;
import com.hanpyeon.academyapi.exception.ErrorCode;

public class MemoMediaException extends BusinessException {
    public MemoMediaException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MemoMediaException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
