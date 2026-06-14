package com.hanpyeon.academyapi.dir.service.media.upload.chunk;

import com.hanpyeon.academyapi.dir.dto.ChunkStoreResult;
import com.hanpyeon.academyapi.dir.exception.ChunkException;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkedFile;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.storage.ChunkStorage;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.storage.ChunkStorageUploader;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChunkHandlerManager {

    private final List<ChunkHandler> chunkPostHandlers;
    private final ChunkStorageUploader chunkStorageUploader;

    public ChunkStoreResult process(final ChunkedFile chunkedFile, final ChunkStorage chunkStorage) {
        log.debug("RUNNED");
        chunkStorageUploader.upload(chunkedFile, chunkStorage);
        return handle(chunkedFile, chunkStorage);
    }

    private ChunkStoreResult handle(final ChunkedFile chunkedFile, final ChunkStorage chunkStorage) {
        return chunkPostHandlers.stream()
                .filter(chunkPostHandler -> chunkPostHandler.applicable(chunkedFile))
                .findAny()
                .orElseThrow(() -> new ChunkException("해당 청크의 처리기를 찾을 수 없음", ErrorCode.CHUNK_HANDLE_EXCEPTION))
                .handle(chunkedFile, chunkStorage);
    }
}
