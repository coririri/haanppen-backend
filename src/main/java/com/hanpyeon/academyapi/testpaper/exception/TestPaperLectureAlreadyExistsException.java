package com.hanpyeon.academyapi.testpaper.exception;

import com.hanpyeon.academyapi.exception.ErrorCode;

public class TestPaperLectureAlreadyExistsException extends TestPaperException {
    public TestPaperLectureAlreadyExistsException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
