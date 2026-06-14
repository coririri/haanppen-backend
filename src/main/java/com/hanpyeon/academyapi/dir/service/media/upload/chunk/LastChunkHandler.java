package com.hanpyeon.academyapi.dir.service.media.upload.chunk;

import com.hanpyeon.academyapi.dir.dto.ChunkStoreResult;
import com.hanpyeon.academyapi.dir.service.media.upload.ChunkedFileTransferManager;
import com.hanpyeon.academyapi.dir.service.media.upload.DirectoryMediaUpdateManager;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkedFile;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.merger.ChunkMerger;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.merger.MergedUploadFile;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.storage.ChunkStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
class LastChunkHandler implements ChunkHandler {
    private final ChunkMerger chunkMerger;
    private final ChunkedFileTransferManager chunkedFileTransferManager;
    private final DirectoryMediaUpdateManager directoryMediaUpdateManager;

    @Override
    @Transactional
    public ChunkStoreResult handle(ChunkedFile chunkedFile, ChunkStorage chunkStorage) {
        log.debug("RUNNED");
        final MergedUploadFile mergedUploadFile = chunkMerger.merge(chunkStorage, chunkedFile.getChunkGroupInfo());
        final String savedFileName = chunkedFileTransferManager.sendToMediaStorage(mergedUploadFile);
        directoryMediaUpdateManager.update(chunkedFile.getChunkGroupInfo(), mergedUploadFile.getDuration(), savedFileName, chunkedFile.getRequestMemberId(), chunkedFile.getChunkGroupInfo().getTotalSize());
        final String userDefinedFileName = chunkedFile.getChunkGroupInfo().getFileName();
        return ChunkStoreResult.completed(savedFileName, userDefinedFileName);
    }

    @Override
    public boolean applicable(ChunkedFile chunkedFile) {
        if (chunkedFile.isLast()) {
            return true;
        }
        return false;
    }
}
