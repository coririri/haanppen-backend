package com.hanpyeon.academyapi.online.service.course.update;

import com.hanpyeon.academyapi.online.dao.OnlineCourse;
import com.hanpyeon.academyapi.online.dto.OnlineCourseInfoUpdateCommand;
import com.hanpyeon.academyapi.online.service.course.MemberLoader;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class OnlineCourseTeacherUpdateHandler implements OnlineCourseUpdateHandler {
    private final MemberLoader memberLoader;

    @Override
    public void update(final OnlineCourse onlineCourse, final OnlineCourseInfoUpdateCommand onlineCourseUpdateCommand) {
        if (Objects.isNull(onlineCourseUpdateCommand.teacherId())) {
            return;
        }
        onlineCourse.setTeacher(memberLoader.findTeacher(onlineCourseUpdateCommand.teacherId()));
    }
}
