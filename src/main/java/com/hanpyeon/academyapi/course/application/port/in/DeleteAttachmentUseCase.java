package com.hanpyeon.academyapi.course.application.port.in;

import com.hanpyeon.academyapi.course.application.dto.DeleteAttachmentCommand;

public interface DeleteAttachmentUseCase {
    void delete(final DeleteAttachmentCommand deleteAttachmentCommand);
}
