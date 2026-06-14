package com.hanpyeon.academyapi.account.model;

import com.hanpyeon.academyapi.account.exceptions.AccountException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.security.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AccountRole {
    private final Role role;

    private static void validate(final Role role) {
        if (Objects.isNull(role)) {
            throw new AccountException("서버 오류", ErrorCode.ACCOUNT_POLICY);
        }
    }

    static AccountRole of(final Role role) {
        if (role == null) {
            return null;
        }
        validate(role);
        return new AccountRole(role);
    }
}
