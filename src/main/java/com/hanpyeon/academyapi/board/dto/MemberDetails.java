package com.hanpyeon.academyapi.board.dto;

import lombok.Builder;

@Builder
public record MemberDetails(
        Long memberId,
        String memberName,
        Integer memberGrade,
        String role
) {}
