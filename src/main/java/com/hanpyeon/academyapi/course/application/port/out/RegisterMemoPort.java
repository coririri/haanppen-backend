package com.hanpyeon.academyapi.course.application.port.out;

import com.hanpyeon.academyapi.course.domain.Memo;

public interface RegisterMemoPort {
    Long register(final Memo memo, final Long courseId);
}
