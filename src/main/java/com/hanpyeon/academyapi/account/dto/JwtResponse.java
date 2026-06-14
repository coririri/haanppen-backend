package com.hanpyeon.academyapi.account.dto;

import com.hanpyeon.academyapi.security.Role;

public record JwtResponse(
        String userName,
        String accessToken,
        Role role
) {
}

