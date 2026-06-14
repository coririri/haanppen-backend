package com.hanpyeon.academyapi.online.dto;

import jakarta.validation.constraints.NotNull;

public record OnlineCategoryAddCommand(
        @NotNull String categoryName,
        Long beforeLevelId
) {
}
