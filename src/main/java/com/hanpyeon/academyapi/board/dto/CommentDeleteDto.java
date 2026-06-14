package com.hanpyeon.academyapi.board.dto;

import com.hanpyeon.academyapi.security.Role;
import lombok.Builder;

@Builder
public record CommentDeleteDto (
        Long commentId,
        Long requestMemberId,
        Role role
){
}
