package com.hanpyeon.academyapi.board.exception;

import com.hanpyeon.academyapi.exception.ErrorCode;

public class NoSuchQuestionException extends BoardException {
    public NoSuchQuestionException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NoSuchQuestionException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
