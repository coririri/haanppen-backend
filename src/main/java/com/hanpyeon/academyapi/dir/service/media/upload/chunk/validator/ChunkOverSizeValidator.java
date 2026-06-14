package com.hanpyeon.academyapi.dir.service.media.upload.chunk.validator;

import com.hanpyeon.academyapi.dir.exception.ChunkException;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkedFile;
import com.hanpyeon.academyapi.exception.ErrorCode;
import org.springframework.stereotype.Service;

@Service
class ChunkOverSizeValidator implements ChunkValidator {
    @Override
    public void validate(ChunkedFile chunkedFile) {
        final Long remainSize = chunkedFile.getRemainSizeWithThisChunk();
        if (remainSize < 0) {
            throw new ChunkException("초과되는 청크 도착 : " + (-remainSize), ErrorCode.CHUNK_SIZE_EXCEPTION);
        }
    }
}
