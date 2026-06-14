package com.hanpyeon.academyapi.course.application.port.out;

public interface DeleteMemoMediaPort {
    void deleteRelatedMedias(final Long memoId);
}
