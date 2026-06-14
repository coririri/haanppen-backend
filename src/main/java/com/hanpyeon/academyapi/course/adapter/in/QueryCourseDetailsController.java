package com.hanpyeon.academyapi.course.adapter.in;

import com.hanpyeon.academyapi.course.application.dto.CourseDetails;
import com.hanpyeon.academyapi.course.application.port.in.LoadCourseDetailsQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "MANAGE COURSE")
class QueryCourseDetailsController {

    private final LoadCourseDetailsQuery loadCourseDetailsQuery;

    @GetMapping("/api/manage/courses/{courseId}")
    @SecurityRequirement(name = "jwtAuth")
    @Operation(summary = "반의 세부사항( 메모 X )을 조회하는 기능입니다", description = "선생님이상의 권한을 가져야 합니다")
    public ResponseEntity<CourseDetails> loadCourseDetails(
            @PathVariable final Long courseId
    ) {
        return ResponseEntity.ok(loadCourseDetailsQuery.loadCourseDetails(courseId));
    }
}
