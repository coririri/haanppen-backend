package com.hanpyeon.academyapi.course.application.port.in;

import com.hanpyeon.academyapi.course.application.dto.DeleteMemoMediaCommand;

public interface DeleteMemoMediaUseCase {
    void delete(final DeleteMemoMediaCommand deleteMemoMediaCommand);
}
