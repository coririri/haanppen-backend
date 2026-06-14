package com.hanpyeon.academyapi.course.application.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AttachmentView {
    private final Long attachmentId;
    private final String fileName;
    private final String mediaSource;
}
