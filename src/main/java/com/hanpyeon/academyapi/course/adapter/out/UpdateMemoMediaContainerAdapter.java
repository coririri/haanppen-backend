package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.course.application.exception.CourseException;
import com.hanpyeon.academyapi.course.application.exception.MemoMediaException;
import com.hanpyeon.academyapi.course.application.port.out.UpdateMemoMediaContainerPort;
import com.hanpyeon.academyapi.course.domain.MemoMedia;
import com.hanpyeon.academyapi.course.domain.MemoMediaContainer;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.media.entity.Media;
import com.hanpyeon.academyapi.media.exception.MediaException;
import com.hanpyeon.academyapi.media.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
class UpdateMemoMediaContainerAdapter implements UpdateMemoMediaContainerPort {
    private final MemoMediaRepository memoMediaRepository;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void save(MemoMediaContainer memoMediaContainer) {
        final List<MemoMedia> unmodifiableMedias = memoMediaContainer.getMemoMedias();

        unmodifiableMedias.stream()
                .forEach(memoMedia -> this.update(memoMedia));
    }

    private void update(final MemoMedia memoMediaDomain) {
        log.debug(memoMediaDomain.toString());
        final com.hanpyeon.academyapi.course.adapter.out.MemoMedia memoMedia = memoMediaRepository.findById(memoMediaDomain.getMemoMediaId())
                        .orElseThrow(() -> new MemoMediaException("해당 수업 미디어를 찾을 수 없음", ErrorCode.MEMO_MEDIA_UPDATE_EXCEPTION));
        log.debug(memoMedia.toString());
        memoMedia.setSequence(memoMediaDomain.getSequence());
        log.debug(memoMedia.toString());
    }
}
