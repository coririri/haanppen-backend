package com.hanpyeon.academyapi.dir.service.media.upload.chunk;

import com.hanpyeon.academyapi.dir.dto.ChunkStoreResult;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkGroupInfo;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkedFile;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.storage.ChunkStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
class IntermediateChunkHandler implements ChunkHandler {

    @Override
    public ChunkStoreResult handle(ChunkedFile chunkedFile, ChunkStorage chunkStorage) {
        log.debug("RUNNED");
        final ChunkGroupInfo chunkGroupInfo = chunkedFile.getChunkGroupInfo();
        final Long needSize = chunkGroupInfo.getRequiringChunkSize();
        final Long nextChunkIndex = chunkGroupInfo.getNextChunkIndex();
        return ChunkStoreResult.need(nextChunkIndex, needSize);
    }

    @Override
    public boolean applicable(ChunkedFile chunkedFile) {
        if (chunkedFile.isLast()) {
            return false;
        }
        return true;
    }
}
