package com.hanpyeon.academyapi.course.application.port.out;

import com.hanpyeon.academyapi.course.application.dto.MemoAppliedDayResult;
import java.time.LocalDate;
import java.util.List;

public interface QueryMemosByMonthPort {
    List<MemoAppliedDayResult> query(final LocalDate startDate, final LocalDate endDate, final Long studentId);
}
