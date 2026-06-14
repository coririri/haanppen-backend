package com.hanpyeon.academyapi.dir.service.media.upload.chunk.storage;

import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkGroup;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkGroupInfo;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkedFile;

public interface ChunkStorage {
    void save(final ChunkedFile chunkedFile);

    ChunkGroup loadSequentialRelatedChunkedFiles(final ChunkGroupInfo chunkGroupInfo);

    void removeChunks(final ChunkGroupInfo chunkGroupInfo);
}
