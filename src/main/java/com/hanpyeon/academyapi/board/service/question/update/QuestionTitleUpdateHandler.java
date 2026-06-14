package com.hanpyeon.academyapi.board.service.question.update;

import com.hanpyeon.academyapi.board.dto.QuestionUpdateDto;
import com.hanpyeon.academyapi.board.entity.Question;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class QuestionTitleUpdateHandler extends QuestionUpdateHandler{
    @Override
    boolean applicable(QuestionUpdateDto questionUpdateDto) {
        return Objects.nonNull(questionUpdateDto.title());
    }

    @Override
    void process(Question question, QuestionUpdateDto questionUpdateDto) {
        question.changeTitle(questionUpdateDto.title());
    }
}
