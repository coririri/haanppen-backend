package com.hanpyeon.academyapi.course.adapter.in;


import com.hanpyeon.academyapi.course.application.dto.CourseUpdateDto;
import com.hanpyeon.academyapi.course.application.port.in.UpdateCourseUseCase;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/manage/courses")
@RequiredArgsConstructor
@Tag(name = "MANAGE COURSE")
class UpdateCourseController {

    private final UpdateCourseUseCase updateCourseNameUseCase;

    @Operation(summary = "반 이름, 담당 선생님을 수정 API", description = "반 이름, 담당 선생님을 수정하는 API 입니다. 바꾸자 하는 담당자가 선생님이 아니라면, 에러가 발생합니다")
    @SecurityRequirement(name = "jwtAuth")
    @PutMapping(value = "/{courseId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateCourseName(
            final @PathVariable Long courseId,
            final @RequestBody CourseUpdateRequest courseUpdateRequest,
            final @AuthenticationPrincipal MemberPrincipal memberPrincipal
    ) {
        final CourseUpdateDto courseUpdateDto = courseUpdateRequest.mapToCourseUpdateDto(courseId, memberPrincipal.memberId());
        updateCourseNameUseCase.updateCourse(courseUpdateDto);

        return ResponseEntity.ok().build();
    }

    static record CourseUpdateRequest(
            String courseName,
            Long newTeacherId
    ) {
        private CourseUpdateDto mapToCourseUpdateDto(final Long courseId, final Long requestMemberId) {
            return new CourseUpdateDto(
                    courseId,
                    requestMemberId,
                    courseName,
                    newTeacherId
            );
        }
    }
}
