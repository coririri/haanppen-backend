package com.hanpyeon.academyapi.account.model;

import com.hanpyeon.academyapi.account.exceptions.AccountException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AccountName {
    private static final int MAX_LENGTH = 10;
    private static final int MIN_LENGTH = 1;
    private final String name;

    private static void validate(final String name) {
        if (Objects.isNull(name)) {
            throw new AccountException(ErrorCode.SERVER_EXCEPTION);
        }
        if (name.length() > MAX_LENGTH || name.length() < MIN_LENGTH) {
            throw new AccountException("요청 된 이름" + name + "최소 " + MIN_LENGTH + "최대 : " + MAX_LENGTH, ErrorCode.ACCOUNT_POLICY);
        }
    }

    public static AccountName of(final String name) {
        if (name == null) {
            return null;
        }
        validate(name);
        return new AccountName(name);
    }
}
