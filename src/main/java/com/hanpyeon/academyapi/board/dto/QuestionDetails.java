package com.hanpyeon.academyapi.board.dto;

import com.hanpyeon.academyapi.media.dto.ImageUrlDto;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record QuestionDetails(
        Long questionId,
        String title,
        String content,
        Boolean solved,
        Long viewCount,
        LocalDateTime registeredDateTime,
        MemberDetails registeredMember,
        MemberDetails targetMember,
        List<CommentDetails> comments,
        List<ImageUrlDto> imageUrls) {
}
