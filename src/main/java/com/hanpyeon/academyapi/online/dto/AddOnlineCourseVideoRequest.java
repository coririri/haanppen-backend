package com.hanpyeon.academyapi.online.dto;

import com.hanpyeon.academyapi.online.dto.AddOnlineVideoCommand.OnlineVideoCommand;
import com.hanpyeon.academyapi.security.Role;

public record AddOnlineCourseVideoRequest(
        Long onlineCourseId,
        OnlineVideoRequest onlineVideoRequest
) {
    public AddOnlineVideoCommand toCommand(final Long requestMemberId, final Role requestMemberRole) {
        return new AddOnlineVideoCommand(
                onlineCourseId,
                this.onlineVideoRequest().toCommand(),
                requestMemberId,
                requestMemberRole
        );
    }

    public record OnlineVideoRequest(
            String videoSrc,
            Boolean isPreview
    ) {
        public OnlineVideoCommand toCommand() {
            return new OnlineVideoCommand(videoSrc, isPreview);
        }
    }
}
