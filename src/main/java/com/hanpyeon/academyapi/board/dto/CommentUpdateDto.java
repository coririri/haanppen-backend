package com.hanpyeon.academyapi.board.dto;

import com.hanpyeon.academyapi.security.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
public record CommentUpdateDto(
        @NotNull Long requestMemberId,
        Role role,
        @NotNull Long commentId,
        String content,
        List<String> imageSources
) {
}
