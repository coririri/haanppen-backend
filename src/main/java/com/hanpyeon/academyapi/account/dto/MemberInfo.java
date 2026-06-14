package com.hanpyeon.academyapi.account.dto;

import com.hanpyeon.academyapi.security.Role;

import java.time.LocalDateTime;

public record MemberInfo(
        Long id,
        String name,
        String phoneNumber,
        Integer grade,
        Role role,
        LocalDateTime registeredDateTime
) {

}
