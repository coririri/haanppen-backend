package com.hanpyeon.academyapi.dir.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UpdateDirectoryDto(
        @NotBlank @Pattern(regexp = "^/+&") String targetDirPath,
        @NotBlank @Pattern(regexp = "^[가-힣a-zA-Z0-9 ]+$") String newDirName,
        @NotNull Long requestMemberId
) {
}
