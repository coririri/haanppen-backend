package com.hanpyeon.academyapi.course.application.port.out;

public interface LoadAttachmentOwnedMemberIdPort {
    Long findOwnerId(final Long attachmentId);
}
