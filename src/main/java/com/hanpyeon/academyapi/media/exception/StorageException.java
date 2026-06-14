package com.hanpyeon.academyapi.media.exception;

import com.hanpyeon.academyapi.exception.BusinessException;
import com.hanpyeon.academyapi.exception.ErrorCode;

public class StorageException extends BusinessException {
    public StorageException(ErrorCode errorCode) {
        super(errorCode);
    }

    public StorageException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
