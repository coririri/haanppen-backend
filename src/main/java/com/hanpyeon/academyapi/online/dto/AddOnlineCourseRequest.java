package com.hanpyeon.academyapi.online.dto;

import com.hanpyeon.academyapi.security.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import java.util.List;

@Schema
public record AddOnlineCourseRequest(
        @Schema(example = "온라인 수업 1")
        @NotNull String courseName,
        @Schema(example = "12")
        @NotNull Long teacherId,
        @Schema(example = "[12, 21, 43, 43]")
        List<Long> students
) {
    public AddOnlineCourseCommand toCommand(final Long requestMemberId, final Role role) {
        return new AddOnlineCourseCommand(requestMemberId, role, this.courseName, this.students, this.teacherId);
    }
}