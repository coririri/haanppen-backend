package com.hanpyeon.academyapi.account.exceptions;

import com.hanpyeon.academyapi.exception.ErrorCode;

public class InvalidAccountPolicy extends AccountException{
    public InvalidAccountPolicy(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidAccountPolicy(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
