package com.hanpyeon.academyapi.account.dto;

public record TeacherQueryDto(Long cursorIndex,
                              Integer pageSize,
                              String name)
{
}
