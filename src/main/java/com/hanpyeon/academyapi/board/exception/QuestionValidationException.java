package com.hanpyeon.academyapi.board.exception;

import com.hanpyeon.academyapi.exception.ErrorCode;

public class QuestionValidationException extends BoardException{
    public QuestionValidationException(ErrorCode errorCode) {
        super(errorCode);
    }

    public QuestionValidationException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
