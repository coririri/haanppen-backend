package com.hanpyeon.academyapi.board.service.question.register;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.board.dao.MemberManager;
import com.hanpyeon.academyapi.board.dto.QuestionRegisterDto;
import com.hanpyeon.academyapi.board.entity.Question;
import com.hanpyeon.academyapi.board.mapper.BoardMapper;
import com.hanpyeon.academyapi.board.service.question.validate.QuestionValidateManager;
import com.hanpyeon.academyapi.media.entity.Image;
import com.hanpyeon.academyapi.media.service.ImageService;
import com.hanpyeon.academyapi.security.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@AllArgsConstructor
public class QuestionRegisterManger {
    private final QuestionValidateManager questionValidateManager;
    private final MemberManager memberManager;
    private final ImageService imageService;
    private final BoardMapper boardMapper;

    @Transactional
    public Question register(final QuestionRegisterDto questionRegisterDto) {
        final Question question = preProcess(questionRegisterDto);
        questionValidateManager.validate(question);
        return question;
    }

    private Question preProcess(final QuestionRegisterDto questionRegisterDto) {
        final Member requestMember = memberManager.getMemberWithRoleValidated(questionRegisterDto.requestMemberId(), Role.STUDENT);
        Member targetMember;
        if (questionRegisterDto.targetMemberId() == null) {
            targetMember = null;
        } else {
            targetMember = memberManager.getMemberWithRoleValidated(questionRegisterDto.targetMemberId(), Role.TEACHER, Role.MANAGER);
        }
        final List<Image> images = imageService.loadImages(questionRegisterDto.images());

        return boardMapper.createQuestion(requestMember, targetMember, images, questionRegisterDto.title(), questionRegisterDto.content());
    }
}
