package com.hanpyeon.academyapi.dir.service.media.upload.chunk.storage;

import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkedFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class ChunkStorageUploader {

    public void upload(final ChunkedFile chunkedFile, final ChunkStorage chunkStorage) {
        log.debug("청크 업로드 시작");
        chunkStorage.save(chunkedFile);
        chunkedFile.updateCurrentInfoChunkIndex();
        log.debug("청크 업로드 완료");
    }
}
