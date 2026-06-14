package com.hanpyeon.academyapi.board.service.comment.adopt;

import com.hanpyeon.academyapi.board.entity.Comment;

public interface CommentAdoptManager {
    void adopt(Comment comment, Long requestMemberId);
}
