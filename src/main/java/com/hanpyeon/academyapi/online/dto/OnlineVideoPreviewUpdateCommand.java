package com.hanpyeon.academyapi.online.dto;

import com.hanpyeon.academyapi.security.Role;
import jakarta.validation.constraints.NotNull;

public record OnlineVideoPreviewUpdateCommand(
        @NotNull Long onlineVideoId,
        @NotNull Boolean previewStatus,
        @NotNull Long requestMemerId,
        @NotNull Role requetMemberRole
) {
}
