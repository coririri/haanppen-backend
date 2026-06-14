package com.hanpyeon.academyapi.security.authentication;

import com.hanpyeon.academyapi.security.Role;

public record MemberPrincipal(
        Long memberId,
        String memberName,
        Role role
) {
    @Override
    public String toString() {
        return "MemberPrincipal{" +
                "memberId=" + memberId +
                ", memberName='" + memberName + '\'' +
                '}';
    }
}
