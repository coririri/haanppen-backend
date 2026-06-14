package com.hanpyeon.academyapi.online.controller;

import com.hanpyeon.academyapi.online.dto.OnlineCourseDetails;
import com.hanpyeon.academyapi.online.dto.OnlineCoursePreview;
import com.hanpyeon.academyapi.online.dto.QueryMyOnlineCourseCommand;
import com.hanpyeon.academyapi.online.dto.QueryOnlineCourseByStudentIdCommand;
import com.hanpyeon.academyapi.online.dto.QueryOnlineCourseByTeacherIdCommand;
import com.hanpyeon.academyapi.online.dto.QueryOnlineCourseDetailsCommand;
import com.hanpyeon.academyapi.online.service.course.OnlineCourseQueryService;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/online-courses")
@RequiredArgsConstructor
@Tag(name = "ONLINE COURSE")
@SecurityRequirement(name = "jwtAuth")
public class OnlineCourseQueryController {
    private final OnlineCourseQueryService queryOnlineCourseService;

    @GetMapping
    @Operation(summary = "모든 온라인 강의를 조회")
    public ResponseEntity<List<OnlineCoursePreview>> queryAllCourses() {
        return ResponseEntity.ok(queryOnlineCourseService.queryAll());
    }

    @GetMapping("/teachers/{teacherId}")
    @Operation(summary = "선생님 ID 를 통해 온라인 강의를 조회")
    public ResponseEntity<List<OnlineCoursePreview>> queryOnlineCoursesByTeacherId(
            @PathVariable final Long teacherId
    ) {
        final QueryOnlineCourseByTeacherIdCommand queryOnlineCourseByTeacherIdCommand =
                new QueryOnlineCourseByTeacherIdCommand(teacherId);
        return ResponseEntity.ok(
                queryOnlineCourseService.queryOnlineCourseByTeacherId(queryOnlineCourseByTeacherIdCommand));
    }

    @GetMapping("/students/{studentId}")
    @Operation(summary = "학생 ID를 통해 온라인 강의 조회")
    public ResponseEntity<List<OnlineCoursePreview>> queryOnlineCoursesByStudentId(
            @PathVariable final Long studentId
    ) {
        final QueryOnlineCourseByStudentIdCommand queryOnlineCourseByStudentIdCommand =
                new QueryOnlineCourseByStudentIdCommand(studentId);

        return ResponseEntity.ok(queryOnlineCourseService.queryOnlineCourseByStudentId(queryOnlineCourseByStudentIdCommand));
    }

    @GetMapping("/my")
    @Operation(summary = "자신이 속한, 혹은 만든 반 조회")
    public ResponseEntity<List<OnlineCoursePreview>> queryOnlineCoursesByStudentId(
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final QueryMyOnlineCourseCommand queryMyOnlineCourseCommand = new QueryMyOnlineCourseCommand(
                memberPrincipal.memberId(), memberPrincipal.role());
        return ResponseEntity.ok(
                queryOnlineCourseService.queryMyOnlineCourses(queryMyOnlineCourseCommand));
    }

    @GetMapping("/{onlineCourseId}")
    @Operation(summary = "특정 반의 세부정보 조회")
    public ResponseEntity<OnlineCourseDetails> queryOnlineCourseDetails(
            @PathVariable(required = true) final Long onlineCourseId
    ) {
        final QueryOnlineCourseDetailsCommand courseDetailsCommand = new QueryOnlineCourseDetailsCommand(onlineCourseId);
        return ResponseEntity.ok(queryOnlineCourseService.queryOnlineCourseDetails(courseDetailsCommand));
    }

    @GetMapping("/categories/{categoryId}")
    @Operation(summary = "카테고리 ID를 통한 반 조회")
    public ResponseEntity<List<OnlineCoursePreview>> queryOnlineCoursesByCategoryId(
            @PathVariable(required = true) final Long categoryId
    ) {
        return ResponseEntity.ok(queryOnlineCourseService.queryOnlineCourseByCategoryId(categoryId));
    }
}
