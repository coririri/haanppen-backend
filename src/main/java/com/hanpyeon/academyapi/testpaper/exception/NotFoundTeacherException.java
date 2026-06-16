package com.hanpyeon.academyapi.testpaper.exception;

import com.hanpyeon.academyapi.exception.ErrorCode;

public class NotFoundTeacherException extends TestPaperException {
    public NotFoundTeacherException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
