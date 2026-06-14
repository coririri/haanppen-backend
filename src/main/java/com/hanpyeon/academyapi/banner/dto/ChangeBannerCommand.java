package com.hanpyeon.academyapi.banner.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChangeBannerCommand(
        @NotNull
        Long bannerId,
        @NotBlank
        String content
) {
}
