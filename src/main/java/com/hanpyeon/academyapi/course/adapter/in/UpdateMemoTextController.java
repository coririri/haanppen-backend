package com.hanpyeon.academyapi.course.adapter.in;

import com.hanpyeon.academyapi.course.application.dto.ModifyMemoTextCommand;
import com.hanpyeon.academyapi.course.application.port.in.ModifyMemoTextUseCase;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "COURSE MEMO")
class UpdateMemoTextController {

    private final ModifyMemoTextUseCase modifyMemoTextUseCase;

    @PutMapping("/api/course/memo")
    public ResponseEntity<?> putMemo(
            @RequestBody @Valid MemoTextModifyRequest memoTextModifyRequest,
            @AuthenticationPrincipal MemberPrincipal memberPrincipal
    ) {
        final ModifyMemoTextCommand command = memoTextModifyRequest.mapToCommand(memberPrincipal.memberId());
        modifyMemoTextUseCase.modify(command);
        return ResponseEntity.ok().build();
    }

    record MemoTextModifyRequest(
            Long memoId,
            String title,
            String content
    ) {
        ModifyMemoTextCommand mapToCommand(final Long requestMemberId) {
            return new ModifyMemoTextCommand(memoId, title, content, requestMemberId);
        }
    }
}
