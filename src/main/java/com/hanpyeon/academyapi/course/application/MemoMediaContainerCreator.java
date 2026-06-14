package com.hanpyeon.academyapi.course.application;

import com.hanpyeon.academyapi.course.application.dto.UpdateMediaMemoCommand;
import com.hanpyeon.academyapi.course.application.exception.MemoMediaException;
import com.hanpyeon.academyapi.course.application.port.out.LoadMemoMediaPort;
import com.hanpyeon.academyapi.course.domain.MemoMedia;
import com.hanpyeon.academyapi.course.domain.MemoMediaContainer;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class MemoMediaContainerCreator {
    private final LoadMemoMediaPort loadMemoMediaPort;

    public MemoMediaContainer createContainer(final UpdateMediaMemoCommand command) {
        final List<MemoMedia> memoMedias = loadMemoMediaPort.loadMedia(command.memoId());
        if (command.mediaSequences().size() != memoMedias.size()) {
            throw new MemoMediaException("요청 된 업데이트 건 : " + command.mediaSequences().size() + "등록된 미디어 갯수 : " + memoMedias.size(), ErrorCode.MEMO_MEDIA_UPDATE_EXCEPTION);
        }
        return MemoMediaContainer.of(memoMedias, command.memoId());
    }

    public MemoMediaContainer createContainer(final Long memoId) {
        final List<MemoMedia> memoMedias = loadMemoMediaPort.loadMedia(memoId);
        return MemoMediaContainer.of(memoMedias, memoId);
    }
}
