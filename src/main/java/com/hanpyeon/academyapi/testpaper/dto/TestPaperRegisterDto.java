package com.hanpyeon.academyapi.testpaper.dto;

import com.hanpyeon.academyapi.security.Role;

import java.util.List;

public record TestPaperRegisterDto(
        String testPaperName,
        Long teacherId,
        List<Long> students,
        Long requestMemberId,
        Role role
) {
}
