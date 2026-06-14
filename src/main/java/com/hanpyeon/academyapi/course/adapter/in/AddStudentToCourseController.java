package com.hanpyeon.academyapi.course.adapter.in;

import com.hanpyeon.academyapi.course.application.dto.RegisterStudentDto;
import com.hanpyeon.academyapi.course.application.port.in.AddStudentToCourseUseCase;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
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

import java.util.List;

@RestController
@RequestMapping("/api/manage/courses")
@RequiredArgsConstructor
@Tag(name = "MANAGE COURSE")
class AddStudentToCourseController {

    private final AddStudentToCourseUseCase addStudentToCourseUseCase;

    @PostMapping(value = "/students", consumes = MediaType.APPLICATION_JSON_VALUE)
    @SecurityRequirement(name = "jwtAuth")
    @Operation(summary = "반에 학생 추가 API", description = "존재하는 반에 학생들을 등록하는 기능입니다. 중복되는 학생들은 중복되어 등록되지 않습니다.")
    public ResponseEntity<?> addStudent(
            @Valid @RequestBody final RegisterStudentRequest registerStudentRequest,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal) {

        final RegisterStudentDto registerStudentDto = registerStudentRequest.createRegisterStudentDto(
                memberPrincipal.memberId(),
                registerStudentRequest.courseId,
                registerStudentRequest.targetStudentIds);

        addStudentToCourseUseCase.addStudentToCourse(registerStudentDto);
        return ResponseEntity.ok(null);
    }

    static record RegisterStudentRequest(
            @NotNull Long courseId,
            @NotNull List<Long> targetStudentIds
            ) {
        private static RegisterStudentDto createRegisterStudentDto(final Long requestMemberId, final Long courseId, final List<Long> targetStudentId) {
            return new RegisterStudentDto(
                    requestMemberId,
                    courseId,
                    targetStudentId);
        }
    }
}
