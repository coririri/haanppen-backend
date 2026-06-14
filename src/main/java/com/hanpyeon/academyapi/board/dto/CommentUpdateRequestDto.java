package com.hanpyeon.academyapi.board.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Schema(description = "댓글 수정 DTO")
public record CommentUpdateRequestDto(
        @NotNull
        Long commentId,
        @Schema(description = "바뀐 전체 본문을 보내야 합니다.")
        String content,
        List<String> imageSources
) {
    @Override
    public String toString() {
        return "CommentUpdateRequestDto{" +
                "commentId=" + commentId +
                ", content='" + content + '\'' +
                '}';
    }
}
