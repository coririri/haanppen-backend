package com.hanpyeon.academyapi.online.dto;

import com.hanpyeon.academyapi.course.application.dto.TeacherPreview;

public record OnlineCoursePreview(
        String courseName,
        Long courseId,
        Integer studentSize,
        TeacherPreview teacherPreview,
        LessonCategoryInfo lessonCategoryInfo,
        String imageSrc
){
}