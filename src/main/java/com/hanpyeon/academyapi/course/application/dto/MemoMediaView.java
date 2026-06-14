package com.hanpyeon.academyapi.course.application.dto;

import java.util.List;

public record MemoMediaView(
        Long memoMediaId,
        String mediaName,
        String mediaSource,
        Integer mediaSequence,
        List<AttachmentView> attachmentViews
) {
}
