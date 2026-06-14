package com.hanpyeon.academyapi.board.service.question.validate;

import com.hanpyeon.academyapi.board.entity.Question;
import com.hanpyeon.academyapi.board.exception.QuestionContentOverSizeException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import org.springframework.stereotype.Component;

@Component
class QuestionContentValidator implements QuestionValidator {
    @Override
    public void validate(Question question) {
        if (question.getContent() == null) {
            return;
        }
        if (question.getContent().length() > 500) {
            throw new QuestionContentOverSizeException("500 이하 여야 합니다", ErrorCode.QUESTION_CONTENT_OVERSIZE);
        }
    }
}
