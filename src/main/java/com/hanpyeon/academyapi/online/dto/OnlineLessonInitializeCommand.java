package com.hanpyeon.academyapi.online.dto;

import com.hanpyeon.academyapi.security.Role;
import jakarta.validation.constraints.NotNull;

public record OnlineLessonInitializeCommand(
        @NotNull Long onlineCourseId,
        @NotNull Long requetMemberId,
        @NotNull Role requetMemberROle
) {
}
