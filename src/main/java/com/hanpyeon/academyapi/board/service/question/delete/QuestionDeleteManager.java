package com.hanpyeon.academyapi.board.service.question.delete;

import com.hanpyeon.academyapi.board.dao.CommentRepository;
import com.hanpyeon.academyapi.board.entity.Question;
import com.hanpyeon.academyapi.board.exception.RequestDeniedException;
import com.hanpyeon.academyapi.board.dao.MemberManager;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.media.service.ImageService;
import com.hanpyeon.academyapi.security.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Component
@AllArgsConstructor
public class QuestionDeleteManager {

    private final MemberManager memberManager;
    private final CommentRepository commentRepository;
    private final ImageService imageService;

    @Transactional
    public void delete(final Question question, final Long requestMemberId) {
        validate(requestMemberId, question.getOwnerMember().getId());
        question.getComments().stream()
                        .forEach(comment -> {
                            comment.delete();
                            commentRepository.delete(comment);
                        });
        imageService.removeImage(question.getImages());
    }

    private void validate(final Long requestMemberId, final Long questionOwnerId) {
        if (requestMemberId.equals(questionOwnerId) || Objects.nonNull(memberManager.getMemberWithRoleValidated(requestMemberId, Role.MANAGER, Role.TEACHER))) {
            return;
        }
        throw new RequestDeniedException(ErrorCode.DENIED_EXCEPTION);
    }
}
