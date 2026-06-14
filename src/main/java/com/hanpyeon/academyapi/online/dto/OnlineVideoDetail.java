package com.hanpyeon.academyapi.online.dto;

import java.util.List;

public record OnlineVideoDetail(
        Long videoId,
        Integer videoSequence,
        String mediaName,
        Boolean isPreview,
        String mediaSrc,
        Long duration,
        List<OnlineVideoAttachmentDetail> attachmentDetails
) {
}
