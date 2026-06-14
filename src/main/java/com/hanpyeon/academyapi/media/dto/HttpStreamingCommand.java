package com.hanpyeon.academyapi.media.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRange;

@RequiredArgsConstructor
public class HttpStreamingCommand implements StreamingCommand {
    private final HttpRange httpRange;
    private final String fileName;

    @Override
    public String getFileName() {
        return fileName;
    }

    @Override
    public Long getStartRangeIndex(Long resourceTotalLength) {
        return httpRange.getRangeStart(resourceTotalLength);
    }

    @Override
    public Long getLastRangeIndex(Long resourceTotalLength) {
        return httpRange.getRangeEnd(resourceTotalLength);
    }

    @Override
    public Long requestMemberId() {
        return null;
    }
}
