package com.hanpyeon.academyapi.board.service.question.update;

import com.hanpyeon.academyapi.board.dto.QuestionUpdateDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * no more used
 */
@ExtendWith(MockitoExtension.class)
class QuestionContentUpdateHandlerTest {

    QuestionContentUpdateHandler questionContentUpdateHandler = new QuestionContentUpdateHandler();

//    @Test
//    void 업데이트_가능_테스트() {
//        final QuestionUpdateDto questionUpdateDto = new QuestionUpdateDto(null, "2", null, null, null);
//
//        assertThat(questionContentUpdateHandler.applicable(questionUpdateDto))
//                .isTrue();
//    }
//
//    @Test
//    void 업데이트_불가_테스트() {
//        final QuestionUpdateDto questionUpdateDto = new QuestionUpdateDto(null, null, null, null, null);
//
//        assertThat(questionContentUpdateHandler.applicable(questionUpdateDto))
//                .isFalse();
//    }
}