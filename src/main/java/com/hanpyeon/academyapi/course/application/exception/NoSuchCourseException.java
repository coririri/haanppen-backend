package com.hanpyeon.academyapi.course.application.exception;

import com.hanpyeon.academyapi.exception.ErrorCode;

public class NoSuchCourseException extends CourseException{
    public NoSuchCourseException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NoSuchCourseException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
