package com.hanpyeon.academyapi.testpaper.dto;

import java.util.List;

public record TestPaperDetails(
        Long testPaperId,
        String testPaperName,
        String teacherName,
        Long teacherId,
        List<StudentPreview> students
) {
}
