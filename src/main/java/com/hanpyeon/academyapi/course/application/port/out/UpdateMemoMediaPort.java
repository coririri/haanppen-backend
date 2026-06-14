package com.hanpyeon.academyapi.course.application.port.out;

import com.hanpyeon.academyapi.course.domain.Memo;

public interface UpdateMemoMediaPort {
    void update(Memo memo);
}
