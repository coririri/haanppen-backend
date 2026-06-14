package com.hanpyeon.academyapi.dir.exception;

import com.hanpyeon.academyapi.exception.BusinessException;
import com.hanpyeon.academyapi.exception.ErrorCode;

public class ChunkException extends BusinessException {

    public ChunkException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ChunkException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
