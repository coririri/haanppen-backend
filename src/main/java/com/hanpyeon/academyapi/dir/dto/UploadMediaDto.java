package com.hanpyeon.academyapi.dir.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;


@RequiredArgsConstructor
@Getter
@ToString
public final class UploadMediaDto {
    private final MultipartFile file;
    private final String fileName;
    private final Long totalChunkCount;
    private final Long currChunkIndex;
    private final Boolean isLast;
    private final Long requestMemberId;
    private final String targetDirectory;
    private final String extension;
    private final Long mediaDuration;
}

