package com.hanpyeon.academyapi.testpaper.exception;

import com.hanpyeon.academyapi.exception.ErrorCode;

public class IllegalTestPaperStudentStateException extends TestPaperException {
    public IllegalTestPaperStudentStateException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
