package com.hanpyeon.academyapi.testpaper.dto;

import com.hanpyeon.academyapi.security.Role;

public record TestPaperUpdateDto(
        Long testPaperId,
        String testPaperName,
        Long teacherId,
        Long requestMemberId,
        Role role
) {
}
