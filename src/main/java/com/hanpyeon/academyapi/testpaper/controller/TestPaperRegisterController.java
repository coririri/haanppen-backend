package com.hanpyeon.academyapi.testpaper.controller;

import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import com.hanpyeon.academyapi.testpaper.dto.TestPaperRegisterDto;
import com.hanpyeon.academyapi.testpaper.service.TestPaperRegisterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/manage/test-papers")
@RequiredArgsConstructor
@Tag(name = "MANAGE TEST PAPER")
public class TestPaperRegisterController {
    private final TestPaperRegisterService testPaperRegisterService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "jwtAuth")
    @Operation(summary = "시험지 반 등록 API", description = "시험지 반을 등록하기 위한 API 입니다. 반 등록은 학생은 허용되지 않습니다.")
    public ResponseEntity<?> registerTestPaper(
            @Valid @RequestBody final TestPaperRegisterRequestDto request,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
    ) {
        final TestPaperRegisterDto dto = new TestPaperRegisterDto(
                request.testPaperName(),
                request.teacherId(),
                request.students(),
                memberPrincipal.memberId(),
                memberPrincipal.role()
        );
        final Long createdTestPaperId = testPaperRegisterService.register(dto);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdTestPaperId)
                .toUri()
        ).build();
    }

    record TestPaperRegisterRequestDto(
            @NotNull String testPaperName,
            @NotNull Long teacherId,
            List<Long> students
    ) {
    }
}
