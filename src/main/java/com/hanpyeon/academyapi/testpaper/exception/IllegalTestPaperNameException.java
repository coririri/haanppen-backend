package com.hanpyeon.academyapi.testpaper.exception;

import com.hanpyeon.academyapi.exception.ErrorCode;

public class IllegalTestPaperNameException extends TestPaperException {
    public IllegalTestPaperNameException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
