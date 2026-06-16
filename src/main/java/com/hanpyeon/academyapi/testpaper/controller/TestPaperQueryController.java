package com.hanpyeon.academyapi.testpaper.controller;

import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import com.hanpyeon.academyapi.testpaper.dto.TestPaperDetails;
import com.hanpyeon.academyapi.testpaper.dto.TestPaperPreview;
import com.hanpyeon.academyapi.testpaper.service.TestPaperQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/test-papers")
@RequiredArgsConstructor
@Tag(name = "TEST PAPER QUERY")
@SecurityRequirement(name = "jwtAuth")
public class TestPaperQueryController {
    private final TestPaperQueryService testPaperQueryService;

    @GetMapping
    @Operation(summary = "시험지 반 목록 조회", description = "사용자 권한에 따라 시험지 반 목록을 조회합니다")
    public ResponseEntity<List<TestPaperPreview>> getTestPapers(
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final List<TestPaperPreview> testPapers = testPaperQueryService.getTestPapersByMemberId(
                memberPrincipal.memberId(),
                memberPrincipal.role()
        );
        return ResponseEntity.ok(testPapers);
    }

    @GetMapping("/{testPaperId}")
    @Operation(summary = "시험지 반 상세 조회", description = "특정 시험지 반의 상세 정보를 조회합니다")
    public ResponseEntity<TestPaperDetails> getTestPaperDetails(
            @PathVariable final Long testPaperId
    ) {
        final TestPaperDetails details = testPaperQueryService.getTestPaperDetails(testPaperId);
        return ResponseEntity.ok(details);
    }

    @GetMapping("/teacher/{teacherId}")
    @Operation(summary = "선생님별 시험지 반 조회", description = "특정 선생님이 담당하는 시험지 반 목록을 조회합니다")
    public ResponseEntity<List<TestPaperPreview>> getTestPapersByTeacher(
            @PathVariable final Long teacherId
    ) {
        final List<TestPaperPreview> testPapers = testPaperQueryService.getTestPapersByTeacherId(teacherId);
        return ResponseEntity.ok(testPapers);
    }
}
