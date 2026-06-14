package com.hanpyeon.academyapi.online.service.course.update;

import com.hanpyeon.academyapi.online.dao.OnlineCourse;
import com.hanpyeon.academyapi.online.dto.OnlineCourseInfoUpdateCommand;

interface OnlineCourseUpdateHandler {
    void update(final OnlineCourse onlineCourse, final OnlineCourseInfoUpdateCommand onlineCourseUpdateCommand);
}
