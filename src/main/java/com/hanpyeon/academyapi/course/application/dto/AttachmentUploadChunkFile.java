package com.hanpyeon.academyapi.course.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AttachmentUploadChunkFile {
    private final MultipartFile file;
    private final String fileName;
    private final Long totalChunkCount;
    private final Long currChunkIndex;
    private final Boolean isLast;
    private final Long requestMemberId;
    private final String extension;

    public static AttachmentUploadChunkFile of(final RegisterAttachmentChunkCommand command) {
        return new AttachmentUploadChunkFile(
                command.chunkedFile(),
                command.fileName(),
                command.totalChunkCount(),
                command.currChunkIndex(),
                command.isLast(),
                command.requestMemberId(),
                command.extension()
        );
    }
}
