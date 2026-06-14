package com.hanpyeon.academyapi.media.service;

import com.hanpyeon.academyapi.media.dto.StreamingCommand;
import com.hanpyeon.academyapi.media.dto.StreamingResult;

public interface MediaService {
    StreamingResult stream(final StreamingCommand streamingCommand);
}
