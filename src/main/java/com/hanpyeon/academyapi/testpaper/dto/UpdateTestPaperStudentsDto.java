package com.hanpyeon.academyapi.testpaper.dto;

import com.hanpyeon.academyapi.security.Role;

import java.util.List;

public record UpdateTestPaperStudentsDto(
        Long testPaperId,
        List<Long> students,
        Long requestMemberId,
        Role role
) {
}
