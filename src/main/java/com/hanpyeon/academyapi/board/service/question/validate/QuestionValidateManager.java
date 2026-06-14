package com.hanpyeon.academyapi.board.service.question.validate;

import com.hanpyeon.academyapi.board.entity.Question;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class QuestionValidateManager {
    private final List<QuestionValidator> validators;

    public void validate(final Question question) {
        validators.stream()
                .forEach(questionValidator -> questionValidator.validate(question));
    }
}
