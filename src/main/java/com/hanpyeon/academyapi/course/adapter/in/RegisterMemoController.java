package com.hanpyeon.academyapi.course.adapter.in;

import com.hanpyeon.academyapi.course.application.dto.MemoRegisterCommand;
import com.hanpyeon.academyapi.course.application.port.in.MemoRegisterUseCase;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Tag(name = "COURSE MEMO")
class RegisterMemoController {

    private final MemoRegisterUseCase memoRegisterUseCase;

    @PostMapping("/api/courses/memos")
    @Operation(summary = "반에 메모를 등록하는 API", description = "메모 등록은 담당 선생님만 가능")
    public ResponseEntity<?> addMemo(
            @AuthenticationPrincipal @NotNull final MemberPrincipal memberPrincipal,
            @RequestBody @Valid final RegisterMemoRequest registerMemoRequest
    ) {
        memoRegisterUseCase.register(registerMemoRequest.createMemoRegisterCommand(memberPrincipal.memberId()));
        return ResponseEntity.ok().build();
    }

    record RegisterMemoRequest(
            @Nonnull Long targetCourseId,
            @Nonnull String title,
            @Nonnull String content,
            @Nonnull LocalDate registerTargetDateTime
//            List<Long> courseMediaIds,
//            List<Long> conceptMediaIds
    ) {
        MemoRegisterCommand createMemoRegisterCommand(final Long requestMemberId) {
            return new MemoRegisterCommand(
                    requestMemberId,
                    this.targetCourseId,
                    this.title,
                    this.content,
                    this.registerTargetDateTime);
        }
    }
}
