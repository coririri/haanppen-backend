package com.hanpyeon.academyapi.course.adapter.in;

import com.hanpyeon.academyapi.course.application.dto.DeleteMemoCommand;
import com.hanpyeon.academyapi.course.application.port.in.DeleteMemoUseCase;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "COURSE MEMO")
class DeleteMemoController {

    private final DeleteMemoUseCase deleteMemoUseCase;

    @DeleteMapping("/api/courses/memos/{targetMemoId}")
    @Operation(summary = "메모 삭제 API")
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<?> deleteMemo(
            @Nonnull @PathVariable final Long targetMemoId,
            @Nonnull @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final DeleteMemoCommand command = new DeleteMemoCommand(memberPrincipal.memberId(), targetMemoId);
        deleteMemoUseCase.delete(command);
        return ResponseEntity.noContent().build();
    }
}
