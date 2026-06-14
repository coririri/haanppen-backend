package com.hanpyeon.academyapi.online.domain;

import com.hanpyeon.academyapi.exception.BusinessException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import java.util.Objects;

public record OnlineCourseTitle(String title) {
    private static final int TITLE_MAX_LENGTH = 50;
    private static final int TITLE_MIN_LENGTH = 1;

    public OnlineCourseTitle {
        validate(title);
    }

    private void validate(final String title) {
        if (Objects.isNull(title)) {
            throw new BusinessException("제목 공백 불가", ErrorCode.ONLINE_COURSE_EXCEPTION);
        }
        if (title.length() > TITLE_MAX_LENGTH) {
            throw new BusinessException("제목 길이 초과 " + TITLE_MAX_LENGTH + " 이하", ErrorCode.ONLINE_COURSE_EXCEPTION);
        }
        if (title.length() < TITLE_MIN_LENGTH) {
            throw new BusinessException("제목 길이 부족 " + TITLE_MIN_LENGTH + " 이상", ErrorCode.ONLINE_COURSE_EXCEPTION);
        }
    }
}
