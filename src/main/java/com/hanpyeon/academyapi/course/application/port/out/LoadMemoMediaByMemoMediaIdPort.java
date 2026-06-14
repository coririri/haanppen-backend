package com.hanpyeon.academyapi.course.application.port.out;

import com.hanpyeon.academyapi.course.domain.MemoMedia;

public interface LoadMemoMediaByMemoMediaIdPort {
    MemoMedia loadByMemoMediaId(final Long memoMediaId, final Long memoId);
}
