package com.hanpyeon.academyapi.testpaper.exception;

import com.hanpyeon.academyapi.exception.ErrorCode;

public class NoSuchTestPaperLectureException extends TestPaperException {
    public NoSuchTestPaperLectureException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
