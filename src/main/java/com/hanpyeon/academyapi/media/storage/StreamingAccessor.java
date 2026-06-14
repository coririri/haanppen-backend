package com.hanpyeon.academyapi.media.storage;

import com.hanpyeon.academyapi.media.dto.StreamingCommand;
import com.hanpyeon.academyapi.media.dto.StreamingResult;

public interface StreamingAccessor {

    StreamingResult getChunk(final StreamingCommand command);
}
