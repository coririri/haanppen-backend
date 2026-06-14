package com.hanpyeon.academyapi.dir.service.media.upload.chunk.merger;

import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkGroupInfo;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.storage.ChunkStorage;

public interface ChunkMerger {
    MergedUploadFile merge(ChunkStorage chunkStorage, ChunkGroupInfo chunkGroupInfo);
}
