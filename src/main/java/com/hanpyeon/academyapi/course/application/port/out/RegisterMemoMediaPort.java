package com.hanpyeon.academyapi.course.application.port.out;

public interface RegisterMemoMediaPort {
    void register(final String mediaSource, final Long memoId);
}
