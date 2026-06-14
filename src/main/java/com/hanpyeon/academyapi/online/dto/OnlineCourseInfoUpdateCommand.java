package com.hanpyeon.academyapi.online.dto;

import com.hanpyeon.academyapi.security.Role;

public record OnlineCourseInfoUpdateCommand(
        Long courseId,
        String courseName,
        Long teacherId,
        Long requestMemberId,
        Role requestMemberRole
) {
}
