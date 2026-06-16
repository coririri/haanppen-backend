package com.hanpyeon.academyapi.testpaper.dto;

public record TestPaperPreview(
        Long testPaperId,
        String testPaperName,
        String teacherName,
        Long teacherId,
        Integer studentCount
) {
}
