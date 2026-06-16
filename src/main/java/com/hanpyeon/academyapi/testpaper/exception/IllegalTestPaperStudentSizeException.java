package com.hanpyeon.academyapi.testpaper.exception;

import com.hanpyeon.academyapi.exception.ErrorCode;

public class IllegalTestPaperStudentSizeException extends TestPaperException {
    public IllegalTestPaperStudentSizeException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
