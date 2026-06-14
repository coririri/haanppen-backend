package com.hanpyeon.academyapi.course.application.port.out;

public interface RegisterMediaAttachmentPort {
    void register(final Long memoMediaId, final String attachmentMediaId);
}
