package com.hanpyeon.academyapi.online.domain;

import com.hanpyeon.academyapi.exception.BusinessException;
import com.hanpyeon.academyapi.exception.ErrorCode;

public record OnlineCourseRange(String range) {
    private static final int RANGE_MAX_SIZE = 100;
    private static final int RANGE_MIN_SIZE = 0;
    public OnlineCourseRange {
        validate(range);
    }
    private void validate(final String rangeContent) {
        if (rangeContent.length() > RANGE_MAX_SIZE) {
            throw new BusinessException("온라인 수업 범위 길이는 최대 " + RANGE_MAX_SIZE, ErrorCode.ONLINE_COURSE_EXCEPTION);
        }
        if (rangeContent.length() < RANGE_MIN_SIZE) {
            throw new BusinessException("온라인 수업 범위 길이는 최소 " + RANGE_MIN_SIZE, ErrorCode.ONLINE_COURSE_EXCEPTION);
        }
    }
}
