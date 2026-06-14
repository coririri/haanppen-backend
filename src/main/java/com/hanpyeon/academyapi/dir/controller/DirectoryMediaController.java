package com.hanpyeon.academyapi.dir.controller;

import com.hanpyeon.academyapi.dir.dto.ChunkStoreResult;
import com.hanpyeon.academyapi.dir.dto.DeleteMediaDto;
import com.hanpyeon.academyapi.dir.dto.UploadMediaDto;
import com.hanpyeon.academyapi.dir.service.MediaService;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class DirectoryMediaController {

    private final MediaService mediaService;

    @PostMapping(value = "/api/directory/media", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    @Operation(summary = "서버의 디렉토리 서비스에 파일을 저장하는 api 입니다.")
    public ResponseEntity<MediaSaveResponse> saveMedia(
            @RequestPart(value = "media") @Nonnull final MultipartFile multipartFile,
            @RequestPart(value = "info") @Valid final MediaSaveRequest request,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final UploadMediaDto mediaSaveDto = request.create(multipartFile, memberPrincipal.memberId());
        final ChunkStoreResult chunkStoreResult = mediaService.uploadChunk(mediaSaveDto);

        return mapToResponse(chunkStoreResult);
    }

    @DeleteMapping(value = "/api/directory/media")
    public ResponseEntity<?> deleteMedia(
            @RequestParam(required = true) final String mediaSrc,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        mediaService.deleteMedia(new DeleteMediaDto(mediaSrc, memberPrincipal));
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<MediaSaveResponse> mapToResponse(final ChunkStoreResult chunkStoreResult) {
        final MediaSaveResponse mediaSaveResponse = MediaSaveResponse.of(chunkStoreResult);
        if (!chunkStoreResult.getIsCompleted() && !chunkStoreResult.getIsWrongChunk()) {
            return ResponseEntity.accepted().body(mediaSaveResponse);
        }
        if (chunkStoreResult.getIsWrongChunk()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(mediaSaveResponse);
        }
        return ResponseEntity.created(null).build();
    }

    record MediaSaveResponse(
            Long nextChunkIndex,
            Long remainSize,
            Boolean needMore,
            Boolean isWrongChunk,
            String errorMessage
    ) {
        static MediaSaveResponse of(final ChunkStoreResult result) {
            return new MediaSaveResponse(
                    result.getNextChunkIndex(),
                    result.getRemainSize(),
                    result.getNeedMore(),
                    result.getIsWrongChunk(),
                    result.getErrorInformation()
            );
        }
    }

    record MediaSaveRequest(
            @NotBlank @Pattern(regexp = "^/.*$") String targetDirectoryPath,
            @NotBlank String fileName,
            @Nonnull Long totalChunkCount,
            @Nonnull Long currChunkIndex,
            @Nonnull Boolean isLast,
            @Nonnull String extension,
            @NonNull Long mediaDuration
    ) {

        UploadMediaDto create(final MultipartFile multipartFile, final Long requestMemberId) {
            return new UploadMediaDto(
                    multipartFile,
                    fileName(),
                    totalChunkCount(),
                    currChunkIndex(),
                    isLast(),
                    requestMemberId,
                    targetDirectoryPath(),
                    extension(),
                    mediaDuration()
            );
        }
    }
}
