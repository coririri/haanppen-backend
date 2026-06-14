package com.hanpyeon.academyapi.account.entity;

import com.hanpyeon.academyapi.account.exceptions.AccountException;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.account.model.Account;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional(propagation = Propagation.MANDATORY)
public class AccountApplier {
    private final MemberRepository memberRepository;
    private final AccountMapper accountMapper;

    /**
     * @param account Account의 필드가 null이 아님이 보장되야 합니다.
     */
    public void applyAccount(final Account account) {
        final Member member = memberRepository.findMemberByIdAndRemovedIsFalse(account.getId())
                .orElseThrow(() -> new AccountException(ErrorCode.NO_SUCH_MEMBER));
        member.setPhoneNumber(account.getPhoneNumber().getPhoneNumber());
        member.setName(account.getAccountName().getName());
        member.setPassword(account.getPassword().getEncryptedPassword());
        member.setGrade(account.getGrade() == null ? null : account.getGrade().getGrade());
    }

    public void save(final Account account) {
        final Member member = accountMapper.mapToMember(account);
        memberRepository.save(member);
    }
}
