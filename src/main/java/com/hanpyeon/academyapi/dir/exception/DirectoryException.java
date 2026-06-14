package com.hanpyeon.academyapi.dir.exception;

import com.hanpyeon.academyapi.exception.BusinessException;
import com.hanpyeon.academyapi.exception.ErrorCode;

public class DirectoryException extends BusinessException {
    public DirectoryException(ErrorCode errorCode) {
        super(errorCode);
    }

    public DirectoryException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
