package com.hanpyeon.academyapi.account.model;

import com.hanpyeon.academyapi.account.exceptions.AccountException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AccountPhoneNumber {
    private static final String PREFIX = "010";
    private static final String PHONE_NUMBER_REGEX ="^[0-9]+$";
    private static final int LENGTH = 11;

    private final String phoneNumber;

    private static void validate(final String phoneNumber) {
        if (phoneNumber == null) {
            throw new AccountException(ErrorCode.ACCOUNT_POLICY);
        }
        if (!phoneNumber.startsWith(PREFIX)) {
            throw new AccountException(ErrorCode.ACCOUNT_POLICY);
        }
        if (phoneNumber.length() != LENGTH) {
            throw new AccountException(ErrorCode.ACCOUNT_POLICY);
        }
        if (!phoneNumber.matches(PHONE_NUMBER_REGEX)) {
            throw new AccountException(ErrorCode.ACCOUNT_POLICY);
        }
    }

    public static AccountPhoneNumber of(final String phoneNumber) {
        if (phoneNumber == null) {
            return null;
        }
        validate(phoneNumber);
        return new AccountPhoneNumber(phoneNumber);
    }
}
