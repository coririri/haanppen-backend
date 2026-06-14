package com.hanpyeon.academyapi.dir.dto;

import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;

public record DeleteMediaDto(
        String mediaSrc,
        MemberPrincipal memberPrincipal
) {
}
