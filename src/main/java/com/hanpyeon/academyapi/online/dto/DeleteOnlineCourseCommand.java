package com.hanpyeon.academyapi.online.dto;

import com.hanpyeon.academyapi.security.Role;

public record DeleteOnlineCourseCommand(
        Long courseId,
        Long requestMemberId,
        Role requestMemberRole
) {

}
