package com.hanpyeon.academyapi.account.model;

import com.hanpyeon.academyapi.account.exceptions.AccountException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ResetAccountPassword {
    @Getter
    private final AccountPassword newPassword;
    private final Password prevRawPassword;

    void isMatchToPrevPassword(final AccountPassword currentPassword) {
        if (!currentPassword.isMatch(prevRawPassword.getPassword())) {
            throw new AccountException(ErrorCode.INVALID_PASSWORD_EXCEPTION);
        }
    }

    public static ResetAccountPassword of(final Password prevRawPassword, final AccountPassword newPassword) {
        if (prevRawPassword == null || prevRawPassword.isBlank()) {
            return null;
        }
        if (newPassword == null) {
            return null;
        }
        return new ResetAccountPassword(newPassword, prevRawPassword);
    }
}
