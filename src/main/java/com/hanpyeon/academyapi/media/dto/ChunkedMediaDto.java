package com.hanpyeon.academyapi.media.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;

@RequiredArgsConstructor
@Getter
public class ChunkedMediaDto {
    private final InputStream inputStream;
    private final Long currentSize;
    private final Long startIndex;
    private final Long endIndex;
}
