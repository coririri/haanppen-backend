package com.hanpyeon.academyapi.media.exception;

import com.hanpyeon.academyapi.exception.ErrorCode;

public class InvalidUploadFileException extends StorageException {
    public InvalidUploadFileException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InvalidUploadFileException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
