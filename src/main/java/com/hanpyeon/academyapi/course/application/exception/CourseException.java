package com.hanpyeon.academyapi.course.application.exception;

import com.hanpyeon.academyapi.exception.BusinessException;
import com.hanpyeon.academyapi.exception.ErrorCode;

public class CourseException extends BusinessException {
    public CourseException(ErrorCode errorCode) {
        super(errorCode);
    }

    public CourseException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
