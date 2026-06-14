package com.hanpyeon.academyapi.media.service;

import com.hanpyeon.academyapi.media.dto.StreamingCommand;
import com.hanpyeon.academyapi.media.dto.StreamingResult;
import com.hanpyeon.academyapi.media.storage.StreamingAccessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {
    private final StreamingAccessor accessor;

    @Override
    public StreamingResult stream(StreamingCommand streamingCommand) {
        validate(streamingCommand.requestMemberId());
        final StreamingResult result = accessor.getChunk(streamingCommand);
        return result;
    }

    private void validate(final Long requestMemberId) {
        // 구현 예정
        return;
    }
}
