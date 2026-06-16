package com.hanpyeon.academyapi.testpaper.exception;

import com.hanpyeon.academyapi.exception.ErrorCode;

public class NoSuchTestPaperException extends TestPaperException {
    public NoSuchTestPaperException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
