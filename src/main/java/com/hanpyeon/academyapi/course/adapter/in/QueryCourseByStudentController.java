package com.hanpyeon.academyapi.course.adapter.in;

import com.hanpyeon.academyapi.course.application.dto.CoursePreview;
import com.hanpyeon.academyapi.course.application.port.in.LoadCoursesByStudentQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "COURSE")
class QueryCourseByStudentController {
    private final LoadCoursesByStudentQuery loadCoursesByStudentQuery;

    @GetMapping("/api/courses/students/{studentId}")
    @Operation(summary = "학생 ID 를 이용한 반 조회", description = "선생님, 원장님만 접근 가능합니다")
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<List<CoursePreview>> loadCourses(
            @PathVariable final Long studentId
    ) {
        return ResponseEntity.ok(loadCoursesByStudentQuery.loadCoursePreviews(studentId));
    }
}
