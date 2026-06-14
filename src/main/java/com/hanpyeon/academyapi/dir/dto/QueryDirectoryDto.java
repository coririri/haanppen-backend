package com.hanpyeon.academyapi.dir.dto;

public record QueryDirectoryDto(
        String path,
        Long requestMemberId
) {
}
