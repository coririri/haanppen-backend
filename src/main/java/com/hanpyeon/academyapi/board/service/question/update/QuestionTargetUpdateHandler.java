package com.hanpyeon.academyapi.board.service.question.update;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.board.dto.QuestionUpdateDto;
import com.hanpyeon.academyapi.board.entity.Question;
import com.hanpyeon.academyapi.board.dao.MemberManager;
import com.hanpyeon.academyapi.security.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
class QuestionTargetUpdateHandler extends QuestionUpdateHandler {

    private final MemberManager memberManager;

    @Override
    boolean applicable(final QuestionUpdateDto questionUpdateDto) {
        return Objects.nonNull(questionUpdateDto.targetMemberId());
    }

    @Override
    void process(final Question question, final QuestionUpdateDto questionUpdateDto) {
        Member member = memberManager.getMemberWithRoleValidated(questionUpdateDto.targetMemberId(), Role.TEACHER, Role.MANAGER);
        question.changeTargetMember(member);
    }
}
