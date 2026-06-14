package com.hanpyeon.academyapi.dir.service.media.upload.chunk.merger;

import com.hanpyeon.academyapi.dir.exception.ChunkException;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkGroup;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkGroupInfo;
import com.hanpyeon.academyapi.dir.service.media.upload.chunk.storage.ChunkStorage;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
class ChunkMergerImpl implements ChunkMerger {

    @Override
    public MergedUploadFile merge(ChunkStorage chunkStorage, ChunkGroupInfo chunkGroupInfo) {
        final ChunkGroup chunkGroup = this.getValidatedChunkGroup(chunkStorage, chunkGroupInfo);
        final InputStream totalInputStream = getCombinedInputStream(chunkGroup.getSequentialChunkInputStream());
        final Long mediaDuration = chunkGroupInfo.getMediaDuration();
        return new MergedUploadFileImpl(chunkGroup.getChunkGroupInfo(), totalInputStream, chunkStorage, mediaDuration);
    }

    private ChunkGroup getValidatedChunkGroup(final ChunkStorage chunkStorage, final ChunkGroupInfo chunkGroupInfo) {
        final ChunkGroup chunkGroup = chunkStorage.loadSequentialRelatedChunkedFiles(chunkGroupInfo);
        try {
            chunkGroup.validateAllChunkFileReceived();
        } catch (ChunkException chunkException) {
            chunkStorage.removeChunks(chunkGroupInfo);
            chunkGroupInfo.clear();
            throw chunkException;
        }
        return chunkGroup;
    }

    private InputStream getCombinedInputStream(final List<InputStream> inputStreams) {
        return new SequenceInputStream(Collections.enumeration(inputStreams));
    }
}
