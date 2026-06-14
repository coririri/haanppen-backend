package com.hanpyeon.academyapi.online.service.lesson.update;

import com.hanpyeon.academyapi.online.dao.OnlineCourse;
import com.hanpyeon.academyapi.online.dto.UpdateOnlineLessonInfoCommand;

interface LessonUpdateHandler {
    void update(final OnlineCourse onlineCourse, final UpdateOnlineLessonInfoCommand updateOnlineLessonInfoCommand);
}
