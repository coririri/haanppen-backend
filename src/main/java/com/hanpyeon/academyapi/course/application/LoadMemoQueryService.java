package com.hanpyeon.academyapi.course.application;

import com.hanpyeon.academyapi.course.application.dto.MemoQueryCommand;
import com.hanpyeon.academyapi.course.application.dto.MemoQueryRequest;
import com.hanpyeon.academyapi.course.application.dto.MemoView;
import com.hanpyeon.academyapi.course.application.port.in.LoadMemoQuery;
import com.hanpyeon.academyapi.course.application.port.out.QueryMemosPort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoadMemoQueryService implements LoadMemoQuery {

    private final QueryMemosPort queryMemosPort;

    @Override
    public Page<MemoView> loadMemos(final @Valid MemoQueryCommand command) {;
        return queryMemosPort.loadMemos(command);
    }
}
