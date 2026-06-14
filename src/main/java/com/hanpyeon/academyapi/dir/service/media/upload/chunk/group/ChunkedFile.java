package com.hanpyeon.academyapi.dir.service.media.upload.chunk.group;

import com.hanpyeon.academyapi.media.service.UploadFile;

public interface ChunkedFile extends UploadFile {
    ChunkGroupInfo getChunkGroupInfo();
    boolean isLast();
    Long getRequestMemberId();
    void validateChunkIndex();
    Long getRemainSizeWithThisChunk();
    void updateCurrentInfoChunkIndex();
}
