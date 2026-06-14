package com.hanpyeon.academyapi.course.adapter.in;

import com.hanpyeon.academyapi.course.application.dto.DeleteMemoMediaCommand;
import com.hanpyeon.academyapi.course.application.port.in.DeleteMemoMediaUseCase;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "COURSE MEMO")
public class DeleteMemoMediaController {

    private final DeleteMemoMediaUseCase deleteMemoMediaUseCase;

    @DeleteMapping("/api/course/memo/{memoId}/media/{memoMediaId}")
    public ResponseEntity<?> deleteMemoMedia(
            @PathVariable Long memoMediaId,
            @PathVariable Long memoId,
            @AuthenticationPrincipal MemberPrincipal memberPrincipal
    ) {
        final DeleteMemoMediaCommand command = create(memoMediaId, memoId, memberPrincipal.memberId());
        deleteMemoMediaUseCase.delete(command);
        return ResponseEntity.noContent().build();
    }

    private DeleteMemoMediaCommand create(final Long memoMediaId, final Long memoId, final Long requestMemberId) {
        return new DeleteMemoMediaCommand(memoMediaId, memoId, requestMemberId);
    }
}
