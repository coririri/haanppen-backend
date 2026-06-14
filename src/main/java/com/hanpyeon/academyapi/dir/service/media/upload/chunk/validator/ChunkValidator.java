package com.hanpyeon.academyapi.dir.service.media.upload.chunk.validator;

import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkedFile;

interface ChunkValidator {
    void validate(final ChunkedFile chunkedFile);
}
