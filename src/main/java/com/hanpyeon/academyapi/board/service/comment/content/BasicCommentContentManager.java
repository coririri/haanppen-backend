package com.hanpyeon.academyapi.board.service.comment.content;

import com.hanpyeon.academyapi.board.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class BasicCommentContentManager implements CommentContentManager {
    @Override
    public void changeContentTo(final Comment comment, final String content) {
        if (changeable(comment)) {
            comment.setContent(content);
        }
    }
    private boolean changeable(final Comment comment) {
        if (comment.getContent().isBlank()) {
            return false;
        }
        return true;
    }
}
