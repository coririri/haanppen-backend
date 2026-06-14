package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.course.application.exception.MemoMediaException;
import com.hanpyeon.academyapi.course.application.port.out.LoadMediaBySourcePort;
import com.hanpyeon.academyapi.course.domain.Media;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.media.repository.MediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
class LoadMediaBySourceAdapter implements LoadMediaBySourcePort {
    private final MediaRepository mediaRepository;

    @Override
    public Media loadMediaBySource(String source) {
        if (Objects.isNull(source)) {
            throw new IllegalArgumentException();
        }

        final com.hanpyeon.academyapi.media.entity.Media media = loadMediaEntity(source);
        // 사이즈 업데이트 예정
        return Media.createByEntity(media.getMediaName(), media.getSrc(), null);
    }

    private com.hanpyeon.academyapi.media.entity.Media loadMediaEntity(final String mediaSource) {
        return mediaRepository.findBySrc(mediaSource)
                .orElseThrow(() -> new MemoMediaException("미디어를 찾을 수 없음 : " + mediaSource, ErrorCode.NO_SUCH_MEDIA));
    }
}
