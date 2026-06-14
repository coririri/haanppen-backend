package com.hanpyeon.academyapi.online.dto;

import com.hanpyeon.academyapi.security.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddOnlineVideoCommand(
        @NotNull Long onlineCourseId,
        @NotBlank OnlineVideoCommand onlineVideoCommand,
        @NotNull Long requestMemberId,
        @NotNull Role requestMemberRole
) {
    public record OnlineVideoCommand(
            String videoSrc,
            Boolean isPreview
    ) {
    }
}
