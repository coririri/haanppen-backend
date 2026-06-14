package com.hanpyeon.academyapi.account.dto;

import org.springframework.data.domain.Pageable;

public record TeacherPageQueryDto(
        String name,
        Pageable pageable
) {
}
