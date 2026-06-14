package com.hanpyeon.academyapi.board.service.question.update;

import com.hanpyeon.academyapi.board.dto.QuestionUpdateDto;
import com.hanpyeon.academyapi.board.entity.Question;
import com.hanpyeon.academyapi.board.exception.RequestDeniedException;
import com.hanpyeon.academyapi.board.service.question.validate.QuestionValidateManager;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.security.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class QuestionUpdateManager {
    private final List<QuestionUpdateHandler> questionUpdateHandlers;
    private final QuestionValidateManager questionValidateManager;

    @Transactional(propagation = Propagation.MANDATORY)
    public void update(final Question targetQuestion, final QuestionUpdateDto questionUpdateDto) {
        verifyAccess(targetQuestion.getOwnerMember().getId(), questionUpdateDto.requestMemberId(), questionUpdateDto.memberRole());
        questionUpdateHandlers.stream()
                .forEach(questionUpdateHandler -> questionUpdateHandler.update(targetQuestion, questionUpdateDto));
        questionValidateManager.validate(targetQuestion);
    }

    private void verifyAccess(final Long ownedMemberId, final Long requestMemberId, final Role role) {
        if (role.equals(Role.ADMIN) || role.equals(Role.TEACHER) || role.equals(Role.MANAGER)) {
            return;
        }
        if (!requestMemberId.equals(ownedMemberId)) {
            throw new RequestDeniedException("본인 질문이 아닙니다", ErrorCode.DENIED_EXCEPTION);
        }
    }
}
