package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.exceptions.NoSuchMemberException;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.course.application.exception.CourseException;
import com.hanpyeon.academyapi.course.application.port.out.ValidateSuperUserPort;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
class ValidateSuperUserAdapter implements ValidateSuperUserPort {
    private final MemberRepository memberRepository;
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public Boolean isSuperUser(Long memberId) {
        if (Objects.isNull(memberId)) {
            throw new CourseException(ErrorCode.INVALID_MEMBER_TARGET);
        }
        final Member member = memberRepository.findMemberByIdAndRemovedIsFalse(memberId)
                .orElseThrow(() -> new NoSuchMemberException("삭제됐거나, 존재하지 않는 ID memberID", ErrorCode.NO_SUCH_MEMBER));
        if (member.getRole().equals(Role.ADMIN)) {
            return true;
        }
        if (member.getRole().equals(Role.MANAGER)) {
            return true;
        }
        return false;
    }
}
