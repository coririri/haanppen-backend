package com.hanpyeon.academyapi.course.domain;

import com.hanpyeon.academyapi.course.application.exception.CourseException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
@Getter
public class Media {
    private final String mediaName;
    private final String mediaSource;
    private final Long mediaSize;
    public static Media createByEntity(final String mediaName, final String mediaSource, final Long mediaSize) {
        return new Media(mediaName, mediaSource, mediaSize);
    }
//    public MemoMedia mapToMemoMedia(final Integer sequence) {
//        if (Objects.isNull(sequence)) {
//            throw new CourseException(ErrorCode.MEMO_NOT_EXIST);
//        }
//        return MemoMedia.createByMedia(this, sequence);
//    }
}
