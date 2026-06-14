package com.hanpyeon.academyapi.course.adapter.in;

import com.hanpyeon.academyapi.course.application.dto.DeleteCourseCommand;
import com.hanpyeon.academyapi.course.application.port.in.DeleteCourseUseCase;
import com.hanpyeon.academyapi.security.Role;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/manage/courses")
@Tag(name = "MANAGE COURSE")
class DeleteCourseController {
    private final DeleteCourseUseCase deleteCourseAdapter;


    @DeleteMapping("/{courseId}")
    @SecurityRequirement(name = "jwtAuth")
    @Operation(summary = "반을 삭제하는 API 입니다", description = "반을 삭제하는 기능은 원장님 권한 이상 필요")
    public ResponseEntity<?> delete(
            @ModelAttribute final DeleteCourseRequest deleteCourseRequest,
            @AuthenticationPrincipal final MemberPrincipal memberPrincipal
            ) {
        final DeleteCourseCommand deleteCommand = deleteCourseRequest.createDeleteCommand(memberPrincipal);
        deleteCourseAdapter.delete(deleteCommand);
        return ResponseEntity.noContent().build();
    }

    private record DeleteCourseRequest(
            Long courseId
    ) {
        private DeleteCourseCommand createDeleteCommand(final MemberPrincipal memberPrincipal) {
            return new DeleteCourseCommand(courseId, memberPrincipal.role(), memberPrincipal.memberId());
        }
    }
}
