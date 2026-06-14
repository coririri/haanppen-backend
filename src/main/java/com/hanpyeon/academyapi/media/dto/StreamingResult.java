package com.hanpyeon.academyapi.media.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;

import java.io.InputStream;

@RequiredArgsConstructor
@Getter
public class StreamingResult {
    private final InputStream inputStream;
    private final MediaType mediaType;
    private final Long currentSize;
    private final Long totalFileSize;
    private final Long startIndex;
    private final Long endIndex;
}
