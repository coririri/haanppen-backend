package com.hanpyeon.academyapi.course.domain;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Getter
@ToString
@Slf4j
public class MemoMedia extends Media {
    private final Long memoMediaId;
    private Integer sequence;

    private MemoMedia(Long memoMediaId, String mediaName, String mediaSource, Long mediaSize, Integer sequence) {
        super(mediaName, mediaSource, mediaSize);
        this.memoMediaId = memoMediaId;
        this.sequence = sequence;
    }

    public int getSequence() {
        return this.sequence;
    }

    void updateSequenceUpperThan(final int targetSequence) {
        if (this.sequence.intValue() > targetSequence) {
            this.sequence --;
        }
    }

    boolean updateSequence(final Long memoMediaId, final Integer newSequence) {
        if (this.memoMediaId.equals(memoMediaId)) {
            this.sequence = newSequence;
            log.debug(memoMediaId + " : " + this.sequence + "->" + newSequence);
            return true;
        }
        return false;
    }

    public static MemoMedia createByEntity(final Long memoMediaId, final String mediaName, final String mediaSource, final Long mediaSize, final Integer sequence) {
        return new MemoMedia(memoMediaId, mediaName, mediaSource, mediaSize, sequence);
    }

    public static MemoMedia createByMedia(final Media media, final Integer sequence) {
        return new MemoMedia(null, media.getMediaName(), media.getMediaSource(), media.getMediaSize(), sequence);
    }
}
