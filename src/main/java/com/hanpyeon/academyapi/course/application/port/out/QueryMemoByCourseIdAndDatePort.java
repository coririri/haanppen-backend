package com.hanpyeon.academyapi.course.application.port.out;

import com.hanpyeon.academyapi.course.application.dto.MemoQueryByCourseIdAndDateCommand;
import com.hanpyeon.academyapi.course.application.dto.MemoView;

public interface QueryMemoByCourseIdAndDatePort {
    MemoView query(final MemoQueryByCourseIdAndDateCommand memoQueryByCourseIdAndDateCommand);
}
