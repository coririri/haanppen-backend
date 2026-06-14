package com.hanpyeon.academyapi.online.dto;

import java.util.List;

public record OnlineCourseDetails(
        Long courseId,
        String courseName,
        List<OnlineStudentPreview> studentPreviews,
        OnlineTeacherPreview teacherPreview
) {
}
