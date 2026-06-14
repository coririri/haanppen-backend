package com.hanpyeon.academyapi.board.service.question.validate;

import com.hanpyeon.academyapi.aspect.log.WarnLoggable;
import com.hanpyeon.academyapi.board.entity.Question;

@WarnLoggable
interface QuestionValidator {
    void validate(Question question);
}
