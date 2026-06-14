package com.hanpyeon.academyapi.board.service.comment.adopt;

import com.hanpyeon.academyapi.board.entity.Comment;
import com.hanpyeon.academyapi.board.exception.AlreadySolvedQuestionException;
import com.hanpyeon.academyapi.board.exception.NotAdoptedCommentException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
class BasicCommentAdoptManager implements CommentAdoptManager {
    @Override
    public void adopt(final Comment targetComment, final Long requestMemberId) {
        isAlreadySolved(targetComment);
        enableSelected(targetComment);
    }

    private void disableSelected(final Comment targetComment) {
        if (!targetComment.getAdopted()) {
            throw new NotAdoptedCommentException(ErrorCode.NOT_ADOPTED_COMMENT);
        }
        targetComment.deAdopt();
    }

    private void enableSelected(final Comment targetComment) {
        targetComment.adopt();
    }

    protected void isAlreadySolved(final Comment comment) {
        if (comment.getQuestion().getSolved() || comment.getAdopted()) {
            throw new AlreadySolvedQuestionException(ErrorCode.ALREADY_SOLVED_QUESTION_EXCEPTION);
        }
    }
}
