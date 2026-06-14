package com.hanpyeon.academyapi.course.adapter.in;

import com.hanpyeon.academyapi.course.application.dto.MemoMediaUpdateSequenceCommand;
import com.hanpyeon.academyapi.course.application.dto.UpdateMediaMemoCommand;
import com.hanpyeon.academyapi.course.application.port.in.UpdateMemoMediaUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Tag(name = "COURSE MEMO")
class UpdateMemoMediaSequenceController {
    private final UpdateMemoMediaUseCase updateMemoMediaUseCase;

    @PutMapping("/api/course/memo/media")
    @Operation(summary = "메모에 관련된 영상을 순서 업데이트 api", description = "요청 시 순서에 맞게 미디어 파일을 보내야 합니다")
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<?> updateMemoMedia(
            @RequestBody @Valid UpdateMemoMediaSequenceController.UpdateMemoMediaRequest updateMemoMediaRequest,
            @AuthenticationPrincipal @Nonnull final Long requestMemberId
    ) {
        final UpdateMediaMemoCommand command = updateMemoMediaRequest.toCommand(requestMemberId);
        updateMemoMediaUseCase.updateMediaMemo(command);
        return ResponseEntity.ok().build();
    }

    record UpdateMemoMediaRequest(
            @Nonnull Long memoId,
            @Nonnull List<MediaSequenceUpdateRequest> sequenceUpdateRequests
    ) {
        UpdateMediaMemoCommand toCommand(final Long requestMemberId) {
            final List<MemoMediaUpdateSequenceCommand> updateMediaMemoCommands = sequenceUpdateRequests.stream()
                    .map(mediaInfo -> mediaInfo.toCommand())
                    .collect(Collectors.toList());
            return new UpdateMediaMemoCommand(memoId, updateMediaMemoCommands, requestMemberId);
        }

        record MediaSequenceUpdateRequest(
                Long memoMediaId,
                Integer sequence
        ) {
            MemoMediaUpdateSequenceCommand toCommand() {
                return new MemoMediaUpdateSequenceCommand(memoMediaId, sequence);
            }
        }
    }
}
