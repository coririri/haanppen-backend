package com.hanpyeon.academyapi.course.application.port.in;

import com.hanpyeon.academyapi.course.application.dto.DeleteMemoCommand;

public interface DeleteMemoUseCase {

    void delete(final DeleteMemoCommand deleteMemoCommand);
}
