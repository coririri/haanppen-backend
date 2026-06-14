package com.hanpyeon.academyapi.course.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AttachmentChunkResult {
    private final Boolean isUploaded;
    private final Boolean isWrongChunk;
    private final Boolean needMore;
    private final String errorMessage;
    private final Long nextRequireChunkIndex;
    private final Long remainSize;

    public static AttachmentChunkResult of(final ChunkProcessedResult result) {
        return new AttachmentChunkResult(
                result.getIsCompleted(),
                result.getIsWrongChunk(),
                result.getNeedMore(),
                result.getErrorMessage(),
                result.getNextRequireChunkIndex(),
                result.getRemainSize()
        );
    }
}
