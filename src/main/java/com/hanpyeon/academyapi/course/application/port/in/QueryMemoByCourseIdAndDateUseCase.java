package com.hanpyeon.academyapi.course.application.port.in;

import com.hanpyeon.academyapi.course.application.dto.MemoQueryByCourseIdAndDateCommand;
import com.hanpyeon.academyapi.course.application.dto.MemoView;

public interface QueryMemoByCourseIdAndDateUseCase {
    MemoView loadSingleMemo(final MemoQueryByCourseIdAndDateCommand memoQueryByCourseIdAndDateCommand);
}
