package com.hanpyeon.academyapi.online.domain;

import java.util.List;
import lombok.Getter;

@Getter
public class OnlineCourseDomain {
    private final OnlineCourseName onlineCourseName;
    private final OnlineCourseStudents onlineCourseStudents;
    private final OnlineCourseTeacher onlineCourseTeacher;

    public OnlineCourseDomain(
            final OnlineCourseName onlineCourseName,
            final OnlineCourseStudents onlineCourseStudents,
            final OnlineCourseTeacher onlineCourseTeacher
    ) {
        this.onlineCourseName = onlineCourseName;
        this.onlineCourseStudents = onlineCourseStudents;
        this.onlineCourseTeacher = onlineCourseTeacher;
    }

    public String getOnlineCourseName() {
        return onlineCourseName.name();
    }

    public List<Long> getStudentIds() {
        return onlineCourseStudents.getOnlineCourseStudents();
    }

    public Long getOnlineCourseTeacherId() {
        return onlineCourseTeacher.getMemberId();
    }
}
