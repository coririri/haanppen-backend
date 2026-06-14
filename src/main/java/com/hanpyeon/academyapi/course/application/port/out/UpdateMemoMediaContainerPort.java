package com.hanpyeon.academyapi.course.application.port.out;

import com.hanpyeon.academyapi.course.domain.MemoMediaContainer;

public interface UpdateMemoMediaContainerPort {
    void save(final MemoMediaContainer memoMediaContainer);
}
