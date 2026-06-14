package com.hanpyeon.academyapi.online.dto;

import com.hanpyeon.academyapi.security.Role;
import jakarta.validation.constraints.NotNull;

public record DeleteOnlineCourseVideoCommand(
        @NotNull Long onlineCourseId,
        @NotNull Long onlineVideoId,
        @NotNull Long requestMemberId,
        @NotNull Role requestMemberRole
) {
}
