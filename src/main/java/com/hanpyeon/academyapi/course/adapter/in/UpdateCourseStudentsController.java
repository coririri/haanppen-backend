package com.hanpyeon.academyapi.course.adapter.in;

import com.hanpyeon.academyapi.course.application.dto.UpdateCourseStudentsDto;
import com.hanpyeon.academyapi.course.application.port.in.UpdateCourseStudentsUseCase;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "COURSE")
class UpdateCourseStudentsController {

    private final UpdateCourseStudentsUseCase updateCourseStudentsUseCase;

    @PutMapping("/api/course/{courseId}/students")
    @Operation(summary = "반 학생 업데이트를 위한 API")
    public ResponseEntity<?> deleteStudents(@AuthenticationPrincipal MemberPrincipal memberPrincipal,
                                            @PathVariable Long courseId,
                                            @RequestBody UpdateCourseStudentsRequest updateCourseStudentsRequest
    ) {
        final UpdateCourseStudentsDto updateCourseStudentsDto = UpdateCourseStudentsDto.of(memberPrincipal.memberId(), courseId, updateCourseStudentsRequest.studentIds);
        updateCourseStudentsUseCase.updateStudents(updateCourseStudentsDto);
        return ResponseEntity.ok(null);
    }

    private record UpdateCourseStudentsRequest(List<Long> studentIds) {
    }
}
