package com.hanpyeon.academyapi.course.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Memo {
    private Long memoId;
    private Course course;
    private LocalDate targetDate;
    private String title;
    private String content;
    private List<MemoMedia> medias;

    public void setMedias(List<MemoMedia> medias) {
        this.medias = medias;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public static Memo createNewMemo(final LocalDate targetDate, final String progressed, final String homework) {
        return new Memo(null, null, targetDate, progressed, homework, new ArrayList<>());
    }

    public static Memo createByEntity(final Long memoId, final Course course, final LocalDate targetDate, final String progressed, final String homework, final List<MemoMedia> medias) {
        return new Memo(memoId, course, targetDate, progressed, homework, medias);
    }
}
