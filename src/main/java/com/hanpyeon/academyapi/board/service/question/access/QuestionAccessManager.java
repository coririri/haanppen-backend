package com.hanpyeon.academyapi.board.service.question.access;

import com.hanpyeon.academyapi.aspect.log.WarnLoggable;
import com.hanpyeon.academyapi.board.dto.QuestionDetails;
import com.hanpyeon.academyapi.board.dto.QuestionPreview;
import com.hanpyeon.academyapi.board.entity.Question;
import com.hanpyeon.academyapi.board.mapper.BoardMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@AllArgsConstructor
public class QuestionAccessManager {

    private final BoardMapper boardMapper;

    public List<QuestionPreview> mapToPreview(final List<Question> questions) {
        return questions.stream().map(boardMapper::createQuestionPreview).toList();
    }

    @WarnLoggable
    @Transactional
    public QuestionDetails getSingle(final Question question) {
        question.addViewCount();
        return boardMapper.createQuestionDetails(question);
    }
}
