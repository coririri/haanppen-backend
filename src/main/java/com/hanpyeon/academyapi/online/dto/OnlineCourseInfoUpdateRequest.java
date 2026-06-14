package com.hanpyeon.academyapi.online.dto;

import com.hanpyeon.academyapi.security.Role;

public record OnlineCourseInfoUpdateRequest(
        String courseName,
        Long newTeacherId
) {
    public OnlineCourseInfoUpdateCommand toCommand(final Long courseId, final Long requestMemberId, final Role requestMemberRole) {
        return new OnlineCourseInfoUpdateCommand(courseId, courseName, newTeacherId, requestMemberId, requestMemberRole);
    }
}
