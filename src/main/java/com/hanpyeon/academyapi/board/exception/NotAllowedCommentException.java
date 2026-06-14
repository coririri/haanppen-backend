package com.hanpyeon.academyapi.board.exception;

import com.hanpyeon.academyapi.exception.ErrorCode;

public class NotAllowedCommentException extends BoardException{
    public NotAllowedCommentException(ErrorCode errorCode) {
        super(errorCode);
    }

    public NotAllowedCommentException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
