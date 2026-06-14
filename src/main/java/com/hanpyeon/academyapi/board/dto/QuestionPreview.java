package com.hanpyeon.academyapi.board.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record QuestionPreview(
        Long questionId,
        String title,
        LocalDateTime registeredDateTime,
        Boolean solved,
        Integer commentCount,
        Long viewCount,
        MemberDetails owner,
        MemberDetails target
) {
}
