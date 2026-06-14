package com.hanpyeon.academyapi.course.application.port.out;

import com.hanpyeon.academyapi.course.application.dto.AttachmentUploadChunkFile;
import com.hanpyeon.academyapi.course.application.dto.ChunkProcessedResult;

public interface ChunkFileHandlerPort {
    ChunkProcessedResult process(final AttachmentUploadChunkFile chunkFile);
}
