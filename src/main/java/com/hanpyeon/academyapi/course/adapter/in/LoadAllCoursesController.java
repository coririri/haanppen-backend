package com.hanpyeon.academyapi.course.adapter.in;

import com.hanpyeon.academyapi.course.application.port.in.QueryAllCourseUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "COURSE")
class LoadAllCoursesController {
    private final QueryAllCourseUseCase queryAllCourseUseCase;

    @GetMapping("/api/courses")
    @Operation(summary = "전체 반을 조회하는 api 입니다", description = "로그인된 모든 사용자가 사용 가능한 API 입니다.")
    @SecurityRequirement(name = "jwtAuth")
    public ResponseEntity<?> getAllCourses() {
        return ResponseEntity.ok(queryAllCourseUseCase.loadAllCoursePreviews());
    }
}
