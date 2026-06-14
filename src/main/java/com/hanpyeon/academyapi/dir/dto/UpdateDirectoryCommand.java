package com.hanpyeon.academyapi.dir.dto;

import com.hanpyeon.academyapi.dir.dao.Directory;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UpdateDirectoryCommand(
        Directory directory,
        @Pattern(regexp = "^/+/&") String newDirName,
        @NotNull Long requestMemberId
) {
}
