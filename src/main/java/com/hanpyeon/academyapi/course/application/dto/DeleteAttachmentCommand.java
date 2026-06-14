package com.hanpyeon.academyapi.course.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class DeleteAttachmentCommand {
    private final Long targetAttachmentId;
    private final Long requestMemberId;

    public static DeleteAttachmentCommand of( final Long targetAttachmentId, final Long requestMemberId) {
        return new DeleteAttachmentCommand( targetAttachmentId, requestMemberId);
    };
}
