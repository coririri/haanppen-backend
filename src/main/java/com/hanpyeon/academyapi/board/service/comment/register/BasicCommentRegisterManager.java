package com.hanpyeon.academyapi.board.service.comment.register;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.board.dao.QuestionRepository;
import com.hanpyeon.academyapi.board.entity.Comment;
import com.hanpyeon.academyapi.board.entity.Question;
import com.hanpyeon.academyapi.board.exception.NotAllowedCommentException;
import com.hanpyeon.academyapi.board.exception.RequestDeniedException;
import com.hanpyeon.academyapi.board.mapper.BoardMapper;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.media.service.ImageService;
import com.hanpyeon.academyapi.security.Role;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
class BasicCommentRegisterManager extends AbstractCommentRegisterManager {
    public BasicCommentRegisterManager(ImageService imageService, QuestionRepository questionRepository, MemberRepository memberRepository, BoardMapper boardMapper) {
        super(imageService, questionRepository, memberRepository, boardMapper);
    }

    @Override
    protected void verifyQuestion(Question question) {
    }

    @Override
    protected void verifyMember(Member member) {
        if (member.getRole().equals(Role.STUDENT)) {
            throw new RequestDeniedException("학생은 질문에 대한 댓글을 달 수 없습니다", ErrorCode.DENIED_EXCEPTION);
        }
    }

    @Override
    protected void verifyComment(Comment comment) {
        if (comment.getContent() == null && comment.getImages().isEmpty()) {
            throw new NotAllowedCommentException("댓글 = 이미지, 글 중 하나는 작성해야 합니다.", ErrorCode.ILLEGAL_COMMENT_EXCEPTION);
        }
    }
}
