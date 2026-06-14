package com.hanpyeon.academyapi.course.application.port.in;

import com.hanpyeon.academyapi.course.application.dto.ModifyMemoTextCommand;

public interface ModifyMemoTextUseCase {
    void modify(final ModifyMemoTextCommand command);
}
