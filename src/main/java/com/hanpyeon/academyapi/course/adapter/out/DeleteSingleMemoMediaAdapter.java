package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.course.application.exception.MemoMediaException;
import com.hanpyeon.academyapi.course.application.port.out.DeleteSingleMemoMediaPort;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class DeleteSingleMemoMediaAdapter implements DeleteSingleMemoMediaPort {

    private final MemoMediaRepository memoMediaRepository;

    @Override
    public void delete(Long memoMediaId) {
        final MemoMedia targetMemoMedia = memoMediaRepository.findById(memoMediaId)
                        .orElseThrow(() -> new MemoMediaException("찾을 수 없음", ErrorCode.MEMO_MEDIA_DELETE_EXCEPTION));
        targetMemoMedia.setNull();
        memoMediaRepository.deleteById(memoMediaId);
    }
}
