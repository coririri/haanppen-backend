package com.hanpyeon.academyapi.course.application.port.in;

import com.hanpyeon.academyapi.course.application.dto.RegisterMemoMediaCommand;

public interface RegisterMemoMediaUseCase {
    void register(final RegisterMemoMediaCommand command);
}
