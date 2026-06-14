package com.hanpyeon.academyapi.board.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
public record CommentRegisterDto (
        @NotNull
        Long questionId,
        @NotNull
        Long memberId,
        String content,
        List<String> images
){

}
