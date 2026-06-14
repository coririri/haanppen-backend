package com.hanpyeon.academyapi.course.application.port.out;

import com.hanpyeon.academyapi.course.domain.MemoMedia;

import java.util.List;

public interface LoadMemoMediaPort {
    List<MemoMedia> loadMedia(final Long memoId);
}
