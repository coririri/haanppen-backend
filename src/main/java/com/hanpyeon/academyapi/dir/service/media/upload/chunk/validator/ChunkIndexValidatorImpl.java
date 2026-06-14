package com.hanpyeon.academyapi.dir.service.media.upload.chunk.validator;

import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkedFile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ChunkIndexValidatorImpl implements ChunkValidator {

    @Override
    public void validate(ChunkedFile chunkedFile) {
        chunkedFile.validateChunkIndex();
    }
}
