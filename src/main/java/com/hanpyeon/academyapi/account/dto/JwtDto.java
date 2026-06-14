package com.hanpyeon.academyapi.account.dto;

import com.hanpyeon.academyapi.security.Role;
import io.swagger.v3.oas.annotations.media.Schema;

public record JwtDto(
        String name,
        @Schema(example = "Bearer *****")
        String accessToken,
        @Schema(example = "Bearer *****")
        String refreshToken,
        Role role
) {

}