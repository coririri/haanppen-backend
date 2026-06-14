package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.course.application.exception.MemoMediaException;
import com.hanpyeon.academyapi.course.application.port.out.RegisterMemoMediaPort;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.media.entity.Media;
import com.hanpyeon.academyapi.media.exception.NoSuchMediaException;
import com.hanpyeon.academyapi.media.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
class RegisterMemoMediaAdapter implements RegisterMemoMediaPort {
    private final MediaRepository mediaRepository;
    private final MemoRepository memoRepository;
    private final MemoMediaRepository memoMediaRepository;
    @Override
    @Transactional
    public void register(String mediaSource, Long memoId) {
        final Media media = mediaRepository.findBySrc(mediaSource)
                .orElseThrow(() -> new NoSuchMediaException(ErrorCode.NO_SUCH_MEDIA));
        final Memo memo = memoRepository.findById(memoId)
                .orElseThrow(() -> new MemoMediaException(ErrorCode.MEMO_NOT_EXIST));
        final List<MemoMedia> memoMedias = memoMediaRepository.findAllByMemo_Id(memoId);
        memoMediaRepository.save(MemoMedia.of(memo, media, memoMedias.size()));
    }
}
