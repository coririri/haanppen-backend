package com.hanpyeon.academyapi.course.application.port.out;

import com.hanpyeon.academyapi.course.application.dto.MemoMediaView;

import java.util.List;

public interface QueryMemoMediaPort {
    List<MemoMediaView> queryMedias(final Long memoId);
}
