package com.hanpyeon.academyapi.account.exceptions;

import com.hanpyeon.academyapi.exception.BusinessException;
import com.hanpyeon.academyapi.exception.ErrorCode;

public class AlreadyRegisteredException extends AccountException {
    public AlreadyRegisteredException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AlreadyRegisteredException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
