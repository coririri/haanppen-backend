package com.hanpyeon.academyapi.course.application.dto;

import java.time.LocalDate;

public record MemoAppliedDayResult(
        Long courseId,
        String courseName,
        Long courseMemoId,
        LocalDate registeredDateTime
) {
}
