package com.hanpyeon.academyapi.testpaper.exception;

import com.hanpyeon.academyapi.exception.BusinessException;
import com.hanpyeon.academyapi.exception.ErrorCode;

public class TestPaperException extends BusinessException {
    public TestPaperException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
