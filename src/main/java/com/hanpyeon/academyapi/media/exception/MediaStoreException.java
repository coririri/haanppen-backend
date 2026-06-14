package com.hanpyeon.academyapi.media.exception;

import com.hanpyeon.academyapi.exception.ErrorCode;

public class MediaStoreException extends StorageException {
    public MediaStoreException(ErrorCode errorCode) {
        super(errorCode);
    }

    public MediaStoreException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
