package com.hanpyeon.academyapi.board.service.question.validate;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.board.entity.Question;
import com.hanpyeon.academyapi.board.exception.InvalidTargetException;
import com.hanpyeon.academyapi.security.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class QuestionTargetMemberValidatorTest {

    QuestionValidator questionTargetMemberValidator = new QuestionTargetMemberValidator();

    @Test
    void 질문_대상이_학생일때_실패_테스트() {
        final Member member = Member.builder().role(Role.STUDENT).build();
        final Question question = Question.builder().targetMember(member).build();

        Assertions.assertThatThrownBy(() -> questionTargetMemberValidator.validate(question))
                .isInstanceOf(InvalidTargetException.class);
    }

    @Test
    void 질문대상_적합_테스트() {
        final Member member = Member.builder().role(Role.TEACHER).build();
        final Question question = Question.builder().targetMember(member).build();

        assertDoesNotThrow(() -> questionTargetMemberValidator.validate(question));
    }
}