package com.hanpyeon.academyapi.testpaper.dto;

import com.hanpyeon.academyapi.dir.dto.FileView;

import java.time.LocalDateTime;
import java.util.List;

public record TestPaperLectureResponse(
        Long lectureId,
        Long testPaperId,
        String testPaperName,
        String lectureName,
        String description,
        String directoryPath,
        LocalDateTime createdTime,
        List<FileView> videos
) {
}
