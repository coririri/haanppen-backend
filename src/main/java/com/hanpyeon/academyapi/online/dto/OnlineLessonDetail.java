package com.hanpyeon.academyapi.online.dto;

import java.util.List;

public record OnlineLessonDetail(
        Long onlineCourseId,
        String title,
        String lessonRange,
        String lessonDesc,
        List<OnlineVideoDetail> onlineVideoDetails,
        LessonCategoryInfo lessonCategoryInfo,
        String imgSrc
) {
}
