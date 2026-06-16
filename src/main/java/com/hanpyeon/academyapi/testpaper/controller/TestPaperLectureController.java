package com.hanpyeon.academyapi.testpaper.controller;

import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import com.hanpyeon.academyapi.testpaper.dto.TestPaperLectureCreateDto;
import com.hanpyeon.academyapi.testpaper.dto.TestPaperLectureResponse;
import com.hanpyeon.academyapi.testpaper.dto.TestPaperLectureUpdateDto;
import com.hanpyeon.academyapi.testpaper.service.TestPaperLectureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/test-papers")
@RequiredArgsConstructor
@Tag(name = "TEST PAPER LECTURE")
@SecurityRequirement(name = "jwtAuth")
public class TestPaperLectureController {
    private final TestPaperLectureService testPaperLectureService;

    @PostMapping("/{testPaperId}/lecture")
    @Operation(summary = "문제집 강의 생성", description = "특정 시험지 반에 문제집 강의를 생성합니다. 각 반당 1개만 생성 가능합니다.")
    public ResponseEntity<?> createLecture(
            @PathVariable final Long testPaperId,
            @Valid @RequestBody final CreateLectureRequest request,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final TestPaperLectureCreateDto dto = new TestPaperLectureCreateDto(
                testPaperId,
                request.lectureName(),
                request.description(),
                request.directoryPath(),
                memberPrincipal.memberId(),
                memberPrincipal.role()
        );
        final Long createdLectureId = testPaperLectureService.createLecture(dto);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdLectureId)
                .toUri()
        ).build();
    }

    @GetMapping("/lectures/{lectureId}")
    @Operation(summary = "문제집 강의 상세 조회", description = "문제집 강의 ID로 상세 정보를 조회합니다")
    public ResponseEntity<TestPaperLectureResponse> getLecture(
            @PathVariable final Long lectureId,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final TestPaperLectureResponse response = testPaperLectureService.getLectureById(lectureId, memberPrincipal.memberId());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{testPaperId}/lecture")
    @Operation(summary = "시험지 반의 문제집 강의 조회", description = "특정 시험지 반의 문제집 강의를 조회합니다")
    public ResponseEntity<TestPaperLectureResponse> getLectureByTestPaper(
            @PathVariable final Long testPaperId,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final TestPaperLectureResponse response = testPaperLectureService.getLectureByTestPaperId(testPaperId, memberPrincipal.memberId());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/lectures/{lectureId}")
    @Operation(summary = "문제집 강의 수정", description = "문제집 강의 정보를 수정합니다")
    public ResponseEntity<?> updateLecture(
            @PathVariable final Long lectureId,
            @Valid @RequestBody final UpdateLectureRequest request,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final TestPaperLectureUpdateDto dto = new TestPaperLectureUpdateDto(
                lectureId,
                request.lectureName(),
                request.description(),
                request.directoryPath(),
                memberPrincipal.memberId(),
                memberPrincipal.role()
        );
        testPaperLectureService.updateLecture(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/lectures/{lectureId}")
    @Operation(summary = "문제집 강의 삭제", description = "문제집 강의를 삭제합니다")
    public ResponseEntity<?> deleteLecture(
            @PathVariable final Long lectureId,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        testPaperLectureService.deleteLecture(lectureId, memberPrincipal.role());
        return ResponseEntity.ok().build();
    }

    record CreateLectureRequest(
            @NotNull String lectureName,
            String description,
            String directoryPath
    ) {
    }

    record UpdateLectureRequest(
            String lectureName,
            String description,
            String directoryPath
    ) {
    }
}
