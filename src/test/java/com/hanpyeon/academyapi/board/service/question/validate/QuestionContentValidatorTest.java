package com.hanpyeon.academyapi.board.service.question.validate;

import com.hanpyeon.academyapi.board.entity.Question;
import com.hanpyeon.academyapi.board.exception.QuestionContentOverSizeException;
import com.hanpyeon.academyapi.board.exception.QuestionValidationException;
import com.hanpyeon.academyapi.media.entity.Image;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

/**
 * no more used
 */
@ExtendWith(MockitoExtension.class)
class QuestionContentValidatorTest {

//    QuestionValidator questionValidator = new QuestionContentValidator();
//    @Test
//    void 글자수_초과_에러처리_테스트() {
//        String exceedSizeContent = "q".repeat(501);
//        final Question question = Question.builder().content(exceedSizeContent).build();
//
//        assertThatThrownBy(() -> questionValidator.validate(question))
//                .isInstanceOf(QuestionContentOverSizeException.class);
//    }
//    @Test
//    void 글자수_경계_테스트() {
//        String exceedSizeContent = "q".repeat(500);
//
//        final Question question = Question.builder().content(exceedSizeContent).build();
//
//        assertDoesNotThrow(() -> questionValidator.validate(question));
//    }
}