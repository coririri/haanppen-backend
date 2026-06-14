package com.hanpyeon.academyapi.course.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class MemoQueryByCourseIdAndDateCommand {
    private final Long courseId;
    private final LocalDate localDate;
}
