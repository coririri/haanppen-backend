package com.hanpyeon.academyapi.course.adapter.in;

import com.hanpyeon.academyapi.course.application.dto.CoursePreview;
import com.hanpyeon.academyapi.course.application.port.in.QueryCourseByMemberIdUseCase;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "COURSE")
class QueryCourseByMemberIdController {
    private final QueryCourseByMemberIdUseCase queryCourseByMemberIdUseCase;
    @GetMapping(value = "/api/courses/my")
    public ResponseEntity<List<CoursePreview>> queryMyCourses(
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
            ) {
        return ResponseEntity.ok(queryCourseByMemberIdUseCase.loadCoursePreviews(memberPrincipal.memberId()));
    }
}
