package com.hanpyeon.academyapi.course.application.port.in;

import com.hanpyeon.academyapi.course.application.dto.MemoAppliedDayResult;
import java.time.LocalDate;
import java.util.List;

public interface QueryCourseByMonthUseCase {
    List<MemoAppliedDayResult> query(final LocalDate registeredDAte, final Long studentId);
}
