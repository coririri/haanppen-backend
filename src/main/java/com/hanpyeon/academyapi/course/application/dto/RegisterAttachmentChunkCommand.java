package com.hanpyeon.academyapi.course.application.dto;

import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public record RegisterAttachmentChunkCommand(
        @Nonnull Long requestMemberId,
        @Nonnull Long memoMediaId,
        @Nonnull MultipartFile chunkedFile,
        @NotBlank String fileName,
        @Nonnull Long totalChunkCount,
        @Nonnull Long currChunkIndex,
        @Nonnull Boolean isLast,
        @Nonnull String extension
){
}
