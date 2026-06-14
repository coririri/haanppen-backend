package com.hanpyeon.academyapi.course.application.port.out;

import com.hanpyeon.academyapi.course.application.dto.AttachmentView;

import java.util.List;

public interface QueryMemoMediaAttachmentPort {
    List<AttachmentView> query(final Long memoMediaId);
}
