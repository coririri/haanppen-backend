package com.hanpyeon.academyapi.board.service.comment.content;

import com.hanpyeon.academyapi.board.entity.Comment;

public interface CommentContentManager {
    void changeContentTo(Comment comment, String content);
}
