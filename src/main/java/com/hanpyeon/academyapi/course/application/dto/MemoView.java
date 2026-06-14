package com.hanpyeon.academyapi.course.application.dto;

import java.time.LocalDate;
import java.util.List;

public record MemoView(
        Long memoId,
        String progressed,
        String homework,
        LocalDate targetDate,
        List<MemoMediaView> memoMediaViews
) {
}
