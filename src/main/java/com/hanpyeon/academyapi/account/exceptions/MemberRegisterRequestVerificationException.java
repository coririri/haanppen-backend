package com.hanpyeon.academyapi.account.exceptions;

import com.hanpyeon.academyapi.exception.ErrorCode;

public class MemberRegisterRequestVerificationException extends AccountException {
    public MemberRegisterRequestVerificationException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MemberRegisterRequestVerificationException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
