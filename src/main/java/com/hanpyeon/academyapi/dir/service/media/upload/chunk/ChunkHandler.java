package com.hanpyeon.academyapi.dir.service.media.upload.chunk;

import com.hanpyeon.academyapi.dir.dto.ChunkStoreResult;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkedFile;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.storage.ChunkStorage;

interface ChunkHandler {

    ChunkStoreResult handle(final ChunkedFile chunkedFile, final ChunkStorage chunkStorage);

    boolean applicable(final ChunkedFile chunkedFile);
}
