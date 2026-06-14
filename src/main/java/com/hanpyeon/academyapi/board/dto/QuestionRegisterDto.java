package com.hanpyeon.academyapi.board.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
public record QuestionRegisterDto(
        @NotNull
        Long requestMemberId,
        @NotNull
        Long targetMemberId,
        String title,
        String content,
        List<String> images
) {}
