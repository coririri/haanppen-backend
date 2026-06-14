package com.hanpyeon.academyapi.account.exceptions;

import com.hanpyeon.academyapi.exception.ErrorCode;

public class InvalidPasswordException extends AccountException {
    public InvalidPasswordException(ErrorCode errorCode) {
        super(errorCode);
    }
}
