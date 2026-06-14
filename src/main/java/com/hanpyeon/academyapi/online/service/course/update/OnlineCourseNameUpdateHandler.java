package com.hanpyeon.academyapi.online.service.course.update;

import com.hanpyeon.academyapi.online.dao.OnlineCourse;
import com.hanpyeon.academyapi.online.domain.OnlineCourseName;
import com.hanpyeon.academyapi.online.dto.OnlineCourseInfoUpdateCommand;
import com.hanpyeon.academyapi.online.service.course.OnlineCourseAbstractFactory;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OnlineCourseNameUpdateHandler implements OnlineCourseUpdateHandler {
    private final OnlineCourseAbstractFactory onlineCourseAbstractFactory;

    @Override
    public void update(final OnlineCourse onlineCourse, final OnlineCourseInfoUpdateCommand onlineCourseUpdateCommand) {
        if (Objects.isNull(onlineCourseUpdateCommand.courseName())) {
            return;
        }
        final OnlineCourseName onlineCourseName = onlineCourseAbstractFactory.toCourseName(
                onlineCourseUpdateCommand.courseName());
        onlineCourse.setCourseName(onlineCourseName.name());
    }
}
