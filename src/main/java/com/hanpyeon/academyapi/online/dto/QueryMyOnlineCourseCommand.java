package com.hanpyeon.academyapi.online.dto;

import com.hanpyeon.academyapi.security.Role;

public record QueryMyOnlineCourseCommand(
        Long requestMemberId,
        Role requestMemberRole
) {
}
