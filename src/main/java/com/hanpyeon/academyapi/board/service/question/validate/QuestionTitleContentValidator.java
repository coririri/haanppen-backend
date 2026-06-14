package com.hanpyeon.academyapi.board.service.question.validate;

import com.hanpyeon.academyapi.board.entity.Question;
import com.hanpyeon.academyapi.board.exception.BoardException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
class QuestionTitleContentValidator implements QuestionValidator {
    @Override
    public void validate(Question question) {
        if (Objects.isNull(question.getTitle()) || question.getTitle().isBlank()) {
            throw new BoardException("현재 질문에는 제목이 포함되야 합니다", ErrorCode.ILLEGAL_QUESTION_EXCEPTION);
        }
    }
}
