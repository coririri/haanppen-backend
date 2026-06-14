package com.hanpyeon.academyapi.board.exception;

import com.hanpyeon.academyapi.exception.ErrorCode;

public class RequestDeniedException extends BoardException{
    public RequestDeniedException(ErrorCode errorCode) {
        super(errorCode);
    }

    public RequestDeniedException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
