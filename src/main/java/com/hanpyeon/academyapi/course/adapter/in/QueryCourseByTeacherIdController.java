package com.hanpyeon.academyapi.course.adapter.in;

import com.hanpyeon.academyapi.course.application.dto.CoursePreview;
import com.hanpyeon.academyapi.course.application.port.in.QueryCourseByMemberIdUseCase;
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
class QueryCourseByTeacherIdController {
    private final QueryCourseByMemberIdUseCase loadCoursesQuery;

    @GetMapping(value = "/api/courses/teachers/{teacherId}")
    @SecurityRequirement(name = "jwtAuth")
    @Operation(summary = "선생님 아이디로 담당 수업 조회 API", description = "선생님 멤버 ID를 통해 관리중인 반 정보를 조회할 수 있습니다. 선생님, 원장님 권한으로 가능합니다.")
    public ResponseEntity<List<CoursePreview>> getCourses(
            @PathVariable final Long teacherId
    ) {
        return ResponseEntity.ok(loadCoursesQuery.loadCoursePreviews(teacherId));
    }
}
