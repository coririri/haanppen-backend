package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.course.application.exception.MemoMediaException;
import com.hanpyeon.academyapi.course.application.port.out.LoadMemoMediaByMemoMediaIdPort;
import com.hanpyeon.academyapi.course.domain.MemoMedia;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class LoadMemoMediaByMemoMediaIdAdapter implements LoadMemoMediaByMemoMediaIdPort {
    private final MemoMediaRepository memoMediaRepository;
    @Override
    public MemoMedia loadByMemoMediaId(Long memoMediaId, Long memoId) {
        final com.hanpyeon.academyapi.course.adapter.out.MemoMedia memoMedia = memoMediaRepository.findMemoMediaByIdAndMemo_Id(memoMediaId, memoId)
                .orElseThrow(() -> new MemoMediaException("존재하지 않는 메모-미디어 입니다.", ErrorCode.MEMO_MEDIA_UPDATE_EXCEPTION));
        return MemoMedia.createByEntity(
                memoMedia.getId(),
                memoMedia.getMedia().getMediaName(),
                memoMedia.getMedia().getSrc(),
                null,
                memoMedia.getSequence()
        );
    }
}
