package com.hanpyeon.academyapi.course.adapter.in;

import com.hanpyeon.academyapi.course.application.dto.AttachmentChunkResult;
import com.hanpyeon.academyapi.course.application.dto.RegisterAttachmentChunkCommand;
import com.hanpyeon.academyapi.course.application.port.in.RegisterAttachmentUseCase;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Tag(name = "COURSE MEMO")
class RegisterAttachmentWithChunkController {

    private final RegisterAttachmentUseCase registerAttachmentUseCase;

    @PostMapping(value = "/api/courses/memos/media/attachment", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<?> registerAttachment(
            @ModelAttribute @Valid final RegisterAttachmentWithChunkController.RegisterAttachmentWithChunkRequest request,
            @AuthenticationPrincipal @Nonnull final MemberPrincipal memberPrincipal
    ) {
        final RegisterAttachmentChunkCommand command = request.createCommand(memberPrincipal.memberId());
        final AttachmentChunkResult result = registerAttachmentUseCase.register(command);

        return getResponse(result);
    }

    private ResponseEntity<RegisterAttachmentChunkResponse> getResponse(final AttachmentChunkResult result) {
        if (result.getIsUploaded()) {
            return ResponseEntity.created(null).build();
        }
        final RegisterAttachmentChunkResponse response = RegisterAttachmentChunkResponse.of(result);
        if (result.getIsWrongChunk()) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
        }
        return ResponseEntity.accepted().body(response);
    }

    record RegisterAttachmentWithChunkRequest(
            @Nonnull Long memoMediaId,
            @NotBlank String fileName,
            @Nonnull MultipartFile chunkedFile,
            @Nonnull Long totalChunkCount,
            @Nonnull Long currChunkIndex,
            @Nonnull Boolean isLast,
            @Nonnull String extension
    ) {
        RegisterAttachmentChunkCommand createCommand(final Long requestMemberId) {
            return new RegisterAttachmentChunkCommand(
                    requestMemberId,
                    memoMediaId(),
                    chunkedFile(),
                    fileName(),
                    totalChunkCount(),
                    currChunkIndex(),
                    isLast(),
                    extension()
            );
        }
    }

    record RegisterAttachmentChunkResponse(
            Long nextChunkIndex,
            Long remainSize,
            Boolean needMore,
            Boolean isWrongChunk,
            String errorMessage
    ) {
        static RegisterAttachmentChunkResponse of(final AttachmentChunkResult result) {
            return new RegisterAttachmentChunkResponse(
                    result.getNextRequireChunkIndex(),
                    result.getRemainSize(),
                    result.getNeedMore(),
                    result.getIsWrongChunk(),
                    result.getErrorMessage()
            );
        }
    }
}
