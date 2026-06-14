package com.hanpyeon.academyapi.account.dto;

import java.time.LocalDateTime;

public record PreviewTeacher(
        Long id,
        String name,
        String phoneNumber,
        LocalDateTime registeredDateTime
) {
    public static PreviewTeacher of(final MemberInfo memberInfo) {
        return new PreviewTeacher(
                memberInfo.id(),
                memberInfo.name(),
                memberInfo.phoneNumber(),
                memberInfo.registeredDateTime()
        );
    }
}
