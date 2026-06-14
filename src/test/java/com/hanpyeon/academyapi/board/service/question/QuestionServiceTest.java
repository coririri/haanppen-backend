package com.hanpyeon.academyapi.board.service.question;

import com.hanpyeon.academyapi.board.dao.QuestionRepository;
import com.hanpyeon.academyapi.board.dto.QuestionDeleteDto;
import com.hanpyeon.academyapi.board.dto.QuestionRegisterDto;
import com.hanpyeon.academyapi.board.dto.QuestionUpdateDto;
import com.hanpyeon.academyapi.board.service.question.access.QuestionAccessManager;
import com.hanpyeon.academyapi.board.service.question.delete.QuestionDeleteManager;
import com.hanpyeon.academyapi.board.service.question.register.QuestionRegisterManger;
import com.hanpyeon.academyapi.board.service.question.update.QuestionUpdateManager;
import com.hanpyeon.academyapi.security.Role;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Collections;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class QuestionServiceTest {
    @Mock
    QuestionUpdateManager questionUpdateManager;
    @Mock
    QuestionRegisterManger questionRegisterManger;
    @Mock
    QuestionAccessManager questionAccessManager;
    @Mock
    QuestionDeleteManager questionDeleteManager;
    @Mock
    QuestionRepository questionRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @InjectMocks
    QuestionService questionService;

    Validator validator;

    @BeforeEach
    public void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @ParameterizedTest
    @MethodSource("provideIllegalQuestionRegisterDto")
    void 질문추가_DTO_검증_실패_테스트(final QuestionRegisterDto questionRegisterDto) {
        assertThat(validator.validate(questionRegisterDto).isEmpty())
                .isFalse();
    }

    @ParameterizedTest
    @MethodSource("provideLegalQuestionRegisterDto")
    void 질문추가_DTO_검증_성공_테스트(final QuestionRegisterDto questionRegisterDto) {
        assertThat(validator.validate(questionRegisterDto).isEmpty())
                .isTrue();
    }

    @Test
    void 질문추가_레포지토리_전달_성공_테스트() {
//        final QuestionRegisterDto questionRegisterDto = Mockito.mock(QuestionRegisterDto.class);
//        final Question question = Question.builder().build();
//
//        Mockito.when(questionRegisterManger.register(questionRegisterDto))
//                        .thenReturn(question);
//        Mockito.when(questionRepository.save(question))
//                        .thenReturn(question);
//
//        questionService.addQuestion(questionRegisterDto);
//

    }

    @ParameterizedTest
    @MethodSource("provideIllegalQuestionUpdateDto")
    void 질문수정_DTO_검증_실패_테스트(final QuestionUpdateDto questionUpdateDto) {
        assertThat(validator.validate(questionUpdateDto).isEmpty())
                .isFalse();
    }

    @ParameterizedTest
    @MethodSource("provideLegalQuestionUpdateDto")
    void 질문수정_DTO_검증_성공_테스트(final QuestionUpdateDto questionUpdateDto) {
        assertThat(validator.validate(questionUpdateDto).isEmpty())
                .isTrue();
    }

    @ParameterizedTest
    @MethodSource("provideIllegalQuestionDeleteDto")
    void 질문삭제_DTO_검증_실패_테스트(final QuestionDeleteDto questionDeleteDto) {
        assertThat(validator.validate(questionDeleteDto).isEmpty())
                .isFalse();
    }

    @ParameterizedTest
    @MethodSource("provideLegalQuestionDeleteDto")
    void 질문삭제_DTO_검증_성공_테스트(final QuestionDeleteDto questionDeleteDto) {
        assertThat(validator.validate(questionDeleteDto).isEmpty())
                .isTrue();
    }

    private static Stream<Arguments> provideIllegalQuestionDeleteDto() {
        return Stream.of(
                Arguments.of(new QuestionDeleteDto(null, Role.ADMIN, null)),
                Arguments.of(new QuestionDeleteDto(1l, Role.MANAGER, null)),
                Arguments.of(new QuestionDeleteDto(null, Role.ADMIN, 2l))
        );
    }

    private static Stream<Arguments> provideLegalQuestionDeleteDto() {
        return Stream.of(
                Arguments.of(new QuestionDeleteDto(2l, Role.TEACHER, 1L))
        );
    }

    private static Stream<Arguments> provideLegalQuestionUpdateDto() {
        return Stream.of(
                Arguments.of(new QuestionUpdateDto(2l, 1L, 3L, Role.STUDENT, "test", "test", Collections.emptyList()))
        );
    }

    private static Stream<Arguments> provideIllegalQuestionUpdateDto() {
        return Stream.of(
                Arguments.of(new QuestionUpdateDto(null, 12L, 2l, Role.STUDENT, "test", "test", Collections.emptyList())),
                Arguments.of(new QuestionUpdateDto(2l, 12L, null, Role.STUDENT, "test", "test", Collections.emptyList())),
                Arguments.of(new QuestionUpdateDto(null, 12L, 2l, Role.STUDENT, "test", "test", Collections.emptyList()))
        );
    }


    private static Stream<Arguments> provideLegalQuestionRegisterDto() {
        return Stream.of(
                Arguments.of(new QuestionRegisterDto(1L, 2L, "test", "test", null)),
                Arguments.of(new QuestionRegisterDto(1L, 1L, "test", "test", Collections.emptyList()))
        );
    }


    private static Stream<Arguments> provideIllegalQuestionRegisterDto() {
        return Stream.of(
                Arguments.of(new QuestionRegisterDto(null, 1L, "test", "test", null)),
                Arguments.of(new QuestionRegisterDto(null, null, "test", "test", null)),
                Arguments.of(new QuestionRegisterDto(2L, null, "test", "test", null)),
                Arguments.of(new QuestionRegisterDto(2L, null, "test", "test", null))
        );
    }
}