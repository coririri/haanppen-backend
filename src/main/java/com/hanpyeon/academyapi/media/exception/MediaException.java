package com.hanpyeon.academyapi.media.exception;

import com.hanpyeon.academyapi.exception.BusinessException;
import com.hanpyeon.academyapi.exception.ErrorCode;

public class MediaException extends BusinessException {
    public MediaException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MediaException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
