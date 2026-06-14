package com.hanpyeon.academyapi.board.dto;

import com.hanpyeon.academyapi.security.Role;
import jakarta.validation.constraints.NotNull;

public record QuestionDeleteDto(
        @NotNull
        Long questionId,
        Role role,
        @NotNull
        Long requestMemberId
) {
}
