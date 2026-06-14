package com.hanpyeon.academyapi.course.application.dto;

import com.hanpyeon.academyapi.dir.dto.ChunkStoreResult;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ChunkProcessedResult {
    private final Boolean isCompleted;
    private final Boolean isWrongChunk;
    private final Boolean needMore;
    private final String errorMessage;
    private final Long nextRequireChunkIndex;
    private final Long remainSize;
    private final String storedName;

    public static ChunkProcessedResult of(final ChunkStoreResult result) {
        return new ChunkProcessedResult(
                result.getIsCompleted(),
                result.getIsWrongChunk(),
                result.getNeedMore(),
                result.getErrorInformation(),
                result.getNextChunkIndex(),
                result.getRemainSize(),
                result.getSavedName()
        );
    }
}

