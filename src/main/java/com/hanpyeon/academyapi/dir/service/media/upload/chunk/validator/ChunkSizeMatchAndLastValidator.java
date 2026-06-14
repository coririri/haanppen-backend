package com.hanpyeon.academyapi.dir.service.media.upload.chunk.validator;

import com.hanpyeon.academyapi.dir.exception.ChunkException;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkedFile;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ChunkSizeMatchAndLastValidator implements ChunkValidator {
    @Override
    public void validate(ChunkedFile chunkedFile) {
        if (!isLegalIntermediateChunk(chunkedFile)) {
            throw new ChunkException("마지막 청크 입니다, isLast 필드를 true 로 해주세요",ErrorCode.CHUNK_ILLEGAL_REQUEST_EXCEPTION);
        }
        if (!isLegalLastChunk(chunkedFile)) {
            throw new ChunkException("마지막 청크가 아닙니다, isLast 필드를 false 로 해주세요",ErrorCode.CHUNK_ILLEGAL_REQUEST_EXCEPTION);
        }
    }

    private boolean isLegalIntermediateChunk(final ChunkedFile chunkedFile) {
        if (chunkedFile.isLast()) {
            return true;
        }
        return !fulfillChunkGroup(chunkedFile);
    }

    private boolean isLegalLastChunk(final ChunkedFile chunkedFile) {
        if (!chunkedFile.isLast()) {
            return true;
        }
        return fulfillChunkGroup(chunkedFile);
    }

    private boolean fulfillChunkGroup(final ChunkedFile chunkedFile) {
        final Long requireSize = chunkedFile.getRemainSizeWithThisChunk();
        log.debug("필요한 사이즈 : "+requireSize);
        if (requireSize == 0) {
            return true;
        }
        return false;
    }
}
