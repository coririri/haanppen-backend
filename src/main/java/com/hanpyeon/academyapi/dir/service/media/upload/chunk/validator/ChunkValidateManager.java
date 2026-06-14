package com.hanpyeon.academyapi.dir.service.media.upload.chunk.validator;

import com.hanpyeon.academyapi.dir.service.media.upload.chunk.group.ChunkedFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ChunkValidateManager {
    private final List<ChunkValidator> chunkValidators;

    public void validate(final ChunkedFile chunkedFile) {
        log.debug("청크 검증 시작");
        chunkValidators.stream()
                .forEach(chunkValidator -> chunkValidator.validate(chunkedFile));
        log.debug("청크 검증 완료");
    }
}
