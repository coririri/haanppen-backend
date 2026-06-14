package com.hanpyeon.academyapi.dir.service.media.upload.chunk.group;

import com.hanpyeon.academyapi.dir.exception.ChunkException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

@Slf4j
public class ChunkGroup {
    private final ChunkGroupInfo chunkGroupInfo;
    private final List<InputStream> chunkInputStreams;
    private final Long groupReceivedFileSize;

    public ChunkGroup(ChunkGroupInfo chunkGroupInfo, List<InputStream> chunkPaths, Long totalSize) {
        this.chunkGroupInfo = chunkGroupInfo;
        this.chunkInputStreams = chunkPaths;
        this.groupReceivedFileSize = totalSize;
    }

    public List<InputStream> getSequentialChunkInputStream() {
        return Collections.unmodifiableList(chunkInputStreams);
    }

    public ChunkGroupInfo getChunkGroupInfo() {
        return chunkGroupInfo;
    }

    public void validateAllChunkFileReceived() {
        if (!this.getChunkGroupInfo().chunkIndexFulfilled() || !groupReceivedFileSize.equals(chunkGroupInfo.getNextChunkIndex() - 1L)) {
            throw new ChunkException("청크 합치는 중 문제 발생, 처음부터 다시 시작해 주세요", ErrorCode.CHUNK_GROUP_EXCEPTION);
        }
    }
}
