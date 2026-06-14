package com.hanpyeon.academyapi.board.service.question.update;

import com.hanpyeon.academyapi.aspect.log.WarnLoggable;
import com.hanpyeon.academyapi.board.dto.QuestionUpdateDto;
import com.hanpyeon.academyapi.board.entity.Question;

@WarnLoggable
abstract class QuestionUpdateHandler {
    abstract boolean applicable(QuestionUpdateDto questionUpdateDto);

    abstract void process(Question question, QuestionUpdateDto questionUpdateDto);

    public final void update(Question question, QuestionUpdateDto questionUpdateDto) {
        if (applicable(questionUpdateDto)) {
            process(question, questionUpdateDto);
        }
    }
}
