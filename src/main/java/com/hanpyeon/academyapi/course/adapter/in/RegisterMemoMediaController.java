package com.hanpyeon.academyapi.course.adapter.in;

import com.hanpyeon.academyapi.course.application.dto.RegisterMemoMediaCommand;
import com.hanpyeon.academyapi.course.application.port.in.RegisterMemoMediaUseCase;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nonnull;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "COURSE MEMO")
public class RegisterMemoMediaController {

    private final RegisterMemoMediaUseCase registerMemoMediaUseCase;

    @PostMapping("/api/course/memo/media")
    public ResponseEntity<?> addMemoMedia(
            @RequestBody @Valid final RegisterMemoMediaRequest request,
            @AuthenticationPrincipal MemberPrincipal memberPrincipal
    ) {
        final RegisterMemoMediaCommand command = request.toCommand(memberPrincipal.memberId());
        registerMemoMediaUseCase.register(command);
        return ResponseEntity.created(null).build();
    }


    record RegisterMemoMediaRequest(
            @Nonnull String mediaSource,
            @Nonnull Long memoId
    ) {
        RegisterMemoMediaCommand toCommand(final Long requestMemberId) {
            return new RegisterMemoMediaCommand(mediaSource, memoId, requestMemberId);
        }
    }
}
