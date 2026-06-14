package com.hanpyeon.academyapi.media.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.InputStream;

@RequiredArgsConstructor
@Getter
public class DownloadResult {
    private final InputStream inputStream;
    private final String fileName;
}
