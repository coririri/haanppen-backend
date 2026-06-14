package com.hanpyeon.academyapi.board.exception;

import com.hanpyeon.academyapi.exception.BusinessException;
import com.hanpyeon.academyapi.exception.ErrorCode;

public class BoardException extends BusinessException {
    public BoardException(ErrorCode errorCode) {
        super(errorCode);
    }

    public BoardException(String detailMessage, ErrorCode errorCode) {
        super(detailMessage, errorCode);
    }
}
