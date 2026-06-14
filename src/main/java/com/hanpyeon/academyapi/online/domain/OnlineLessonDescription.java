package com.hanpyeon.academyapi.online.domain;

import com.hanpyeon.academyapi.exception.BusinessException;
import com.hanpyeon.academyapi.exception.ErrorCode;

public record OnlineLessonDescription(String description) {
    private static final int DESCRIPTION_MAX_LENGTH = 500;
    private static final int DESCRIPTION_MIN_LENGTH = 0;

    public OnlineLessonDescription {
        validate(description);
    }
    private void validate(final String description) {
        if (description.length() > DESCRIPTION_MAX_LENGTH) {
            throw new BusinessException("온라인 수업 설명 길이는 최대 " + DESCRIPTION_MAX_LENGTH, ErrorCode.ONLINE_COURSE_EXCEPTION);
        }
        if (description.length() < DESCRIPTION_MIN_LENGTH) {
            throw new BusinessException("온라인 수업 설명 길이는 최소 " + DESCRIPTION_MIN_LENGTH, ErrorCode.ONLINE_COURSE_EXCEPTION);
        }
    }
}
