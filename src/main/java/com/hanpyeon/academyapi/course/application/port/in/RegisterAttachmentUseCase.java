package com.hanpyeon.academyapi.course.application.port.in;

import com.hanpyeon.academyapi.course.application.dto.AttachmentChunkResult;
import com.hanpyeon.academyapi.course.application.dto.RegisterAttachmentChunkCommand;

public interface RegisterAttachmentUseCase {
    AttachmentChunkResult register(final RegisterAttachmentChunkCommand registerAttachmentCommand);
}
