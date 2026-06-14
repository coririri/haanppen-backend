package com.hanpyeon.academyapi.account.model;

import com.hanpyeon.academyapi.account.exceptions.AccountException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.security.PasswordHandler;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AccountPassword {
    private static final int MAX_LENGTH = 20;
    private static final int MIN_LENGTH = 4;
    private final PasswordHandler passwordHandler;
    private String encryptedPassword;

    public boolean isMatch(final String rawPassword) {
        return passwordHandler.matches(rawPassword, encryptedPassword);
    }

    protected static void validate(final String rawPassword) {
        if (rawPassword == null || rawPassword.isBlank()) {
            return;
        }
        if (rawPassword.length() > MAX_LENGTH || rawPassword.length() < MIN_LENGTH) {
            throw new AccountException("길이 오류. 길이는 " + MIN_LENGTH + " 와 " + MAX_LENGTH + "사이여아합니다.", ErrorCode.ACCOUNT_POLICY);
        }
    }

    public static AccountPassword createNew(final Password rawPassword, final PasswordHandler passwordHandler) {
        if (rawPassword == null || rawPassword.isBlank()) {
            return null;
        }
        validate(rawPassword.getPassword());
        return new AccountPassword(passwordHandler, passwordHandler.getEncodedPassword(rawPassword.getPassword()));
    }

    public static AccountPassword load(final String encryptedPassword, final PasswordHandler passwordHandler) {
        return new AccountPassword(passwordHandler, encryptedPassword);
    }
}
