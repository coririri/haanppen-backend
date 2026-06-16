package com.hanpyeon.academyapi.testpaper.controller;

import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import com.hanpyeon.academyapi.testpaper.dto.TestPaperUpdateDto;
import com.hanpyeon.academyapi.testpaper.dto.UpdateTestPaperStudentsDto;
import com.hanpyeon.academyapi.testpaper.service.TestPaperUpdateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manage/test-papers")
@RequiredArgsConstructor
@Tag(name = "MANAGE TEST PAPER")
@SecurityRequirement(name = "jwtAuth")
public class TestPaperUpdateController {
    private final TestPaperUpdateService testPaperUpdateService;

    @PutMapping("/{testPaperId}")
    @Operation(summary = "시험지 반 정보 수정", description = "시험지 반의 이름과 담당 선생님을 수정합니다")
    public ResponseEntity<?> updateTestPaper(
            @PathVariable final Long testPaperId,
            @Valid @RequestBody final TestPaperUpdateRequestDto request,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final TestPaperUpdateDto dto = new TestPaperUpdateDto(
                testPaperId,
                request.testPaperName(),
                request.teacherId(),
                memberPrincipal.memberId(),
                memberPrincipal.role()
        );
        testPaperUpdateService.updateTestPaper(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{testPaperId}/students")
    @Operation(summary = "시험지 반 학생 목록 수정", description = "시험지 반의 학생 목록을 수정합니다")
    public ResponseEntity<?> updateTestPaperStudents(
            @PathVariable final Long testPaperId,
            @Valid @RequestBody final UpdateTestPaperStudentsRequestDto request,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final UpdateTestPaperStudentsDto dto = new UpdateTestPaperStudentsDto(
                testPaperId,
                request.students(),
                memberPrincipal.memberId(),
                memberPrincipal.role()
        );
        testPaperUpdateService.updateTestPaperStudents(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{testPaperId}")
    @Operation(summary = "시험지 반 삭제", description = "시험지 반을 삭제합니다")
    public ResponseEntity<?> deleteTestPaper(
            @PathVariable final Long testPaperId,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        testPaperUpdateService.deleteTestPaper(testPaperId, memberPrincipal.role());
        return ResponseEntity.ok().build();
    }

    record TestPaperUpdateRequestDto(
            String testPaperName,
            Long teacherId
    ) {
    }

    record UpdateTestPaperStudentsRequestDto(
            List<Long> students
    ) {
    }
}
