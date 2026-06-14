package com.hanpyeon.academyapi.course.application.dto;

import java.time.LocalDate;

public record MemoRegisterCommand(
        Long requestMemberId,
        Long targetCourseId,
        String title,
        String content,
        LocalDate registerTargetDate
//        List<Long> courseMediaIds,
//        List<Long> conceptMediaIds
) {
}
