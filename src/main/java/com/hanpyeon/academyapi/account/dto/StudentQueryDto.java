package com.hanpyeon.academyapi.account.dto;

public record StudentQueryDto(Long cursorIndex,
                              Integer pageSize,
                              String name,
                              Integer startGrade,
                              Integer endGrade) {
}
