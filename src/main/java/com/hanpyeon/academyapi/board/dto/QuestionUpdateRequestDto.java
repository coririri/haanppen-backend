package com.hanpyeon.academyapi.board.dto;

import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record QuestionUpdateRequestDto(
        @NotNull
        Long questionId,
        String title,
        String content,
        Long targetMemberId,
        List<String> imageSources
) {
    @Override
    public String toString() {
        return "QuestionUpdateRequestDto{" +
                "content='" + content + '\'' +
                ", targetMemberId=" + targetMemberId +
                '}';
    }
}
