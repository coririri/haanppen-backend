package com.hanpyeon.academyapi.course.application.exception;

import com.hanpyeon.academyapi.exception.ErrorCode;

public class IllegalCourseNameException extends CourseException{
    public IllegalCourseNameException(ErrorCode errorCode) {
        super(errorCode);
    }

    public IllegalCourseNameException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
