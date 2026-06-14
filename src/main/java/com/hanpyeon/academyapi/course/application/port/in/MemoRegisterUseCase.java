package com.hanpyeon.academyapi.course.application.port.in;

import com.hanpyeon.academyapi.course.application.dto.MemoRegisterCommand;

public interface MemoRegisterUseCase {
    Long register(final MemoRegisterCommand memoRegisterCommand);
}
