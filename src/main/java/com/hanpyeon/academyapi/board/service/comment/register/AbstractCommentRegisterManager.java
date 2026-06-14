package com.hanpyeon.academyapi.board.service.comment.register;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.board.dao.QuestionRepository;
import com.hanpyeon.academyapi.board.dto.CommentRegisterDto;
import com.hanpyeon.academyapi.board.entity.Comment;
import com.hanpyeon.academyapi.board.entity.Question;
import com.hanpyeon.academyapi.board.exception.NoSuchMemberException;
import com.hanpyeon.academyapi.board.exception.NoSuchQuestionException;
import com.hanpyeon.academyapi.board.mapper.BoardMapper;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.media.entity.Image;
import com.hanpyeon.academyapi.media.exception.MediaStoreException;
import com.hanpyeon.academyapi.media.service.ImageService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;


/**
 * 댓글 등록을 위해 제공되는 클래스입니다.
 * 해당 클래스를 이용하여 댓글에 대한 여러 제약을 구현할 수 있습니다.
 */
@AllArgsConstructor
@Slf4j
abstract class AbstractCommentRegisterManager implements CommentRegisterManager {
    private final ImageService imageService;
    private final QuestionRepository questionRepository;
    private final MemberRepository memberRepository;
    private final BoardMapper boardMapper;

    @Override
    @Transactional
    public Comment register(final CommentRegisterDto commentRegisterDto) {
        Question question = findQuestion(commentRegisterDto.questionId());
        Member member = findMember(commentRegisterDto.memberId());
        if (!imageService.isExists(commentRegisterDto.images())) {
            log.error(" cannt find images Source that included in comment : " + commentRegisterDto);
            throw new MediaStoreException("이미지를 찾을 수 없습니다.", ErrorCode.MEDIA_ACCESS_EXCEPTION);
        }
        final List<Image> images = imageService.loadImages(commentRegisterDto.images());
        final Comment comment = boardMapper.createComment(question, member, images, commentRegisterDto.content());
        question.addComment(comment);
        question.solved();
        verifyComment(comment);
        return comment;
    }

    private Question findQuestion(final Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new NoSuchQuestionException(ErrorCode.NO_SUCH_QUESTION));
        verifyQuestion(question);
        return question;
    }

    private Member findMember(final Long memberId) {
        Member member = memberRepository.findMemberByIdAndRemovedIsFalse(memberId)
                .orElseThrow(() -> new NoSuchMemberException(ErrorCode.NO_SUCH_MEMBER));
        verifyMember(member);
        return member;
    }

    protected abstract void verifyQuestion(final Question question);

    protected abstract void verifyMember(final Member member);
    protected abstract void verifyComment(final Comment comment);
}
