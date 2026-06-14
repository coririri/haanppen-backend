package com.hanpyeon.academyapi.board.dao;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.board.exception.NoSuchMemberException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.security.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.function.Predicate;

@Component
@AllArgsConstructor
public class MemberManager {

    private final MemberRepository memberRepository;

    public Member getMemberWithValidated(final Long id, final Predicate<? super Member> predicate) {
        return memberRepository.findById(id)
                .filter(predicate)
                .orElseThrow(() -> new NoSuchMemberException(predicate + " : 조건에 맞는 사용자를 찾을 수 없습니다.", ErrorCode.NO_SUCH_MEMBER));
    }
    public Member getMemberWithRoleValidated(final Long memberId, final Role... roles) {
        return getMemberWithValidated(
                memberId, member -> Arrays.stream(roles).anyMatch(role -> role.equals(member.getRole()))
        );
    }
}
