package com.hanpyeon.academyapi.board.exception;

import com.hanpyeon.academyapi.exception.ErrorCode;

public class AlreadySolvedQuestionException extends BoardException{
    public AlreadySolvedQuestionException(ErrorCode errorCode) {
        super(errorCode);
    }

    public AlreadySolvedQuestionException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
