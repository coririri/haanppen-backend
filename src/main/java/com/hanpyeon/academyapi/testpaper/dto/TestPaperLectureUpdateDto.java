package com.hanpyeon.academyapi.testpaper.dto;

import com.hanpyeon.academyapi.security.Role;

public record TestPaperLectureUpdateDto(
        Long lectureId,
        String lectureName,
        String description,
        String directoryPath,
        Long requestMemberId,
        Role role
) {
}
