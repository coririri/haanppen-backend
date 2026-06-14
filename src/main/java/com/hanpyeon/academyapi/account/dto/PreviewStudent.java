package com.hanpyeon.academyapi.account.dto;

import com.hanpyeon.academyapi.account.entity.Member;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PreviewStudent(
        Long id,
        String name,
        String phoneNumber,
        Integer grade,
        LocalDateTime registeredDateTime
) {

    public static PreviewStudent of(final MemberInfo memberInfo) {
        return new PreviewStudent(
                memberInfo.id(),
                memberInfo.name(),
                memberInfo.phoneNumber(),
                memberInfo.grade(),
                memberInfo.registeredDateTime()
        );
    }
}
