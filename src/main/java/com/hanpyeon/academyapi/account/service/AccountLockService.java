package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.model.Account;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.board.exception.NoSuchMemberException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class AccountLockService {
    private final MemberRepository memberRepository;
    private final Integer maxTryCount;
    private final Long lockTimeMinutes;

    public AccountLockService(
            MemberRepository memberRepository,
            @Value("${login.lock.maxTryCount}") Integer maxTryCount,
            @Value("${login.lock.minutes}") final Long lockTimeMinutes
    ) {
        this.memberRepository = memberRepository;
        this.maxTryCount = maxTryCount;
        this.lockTimeMinutes = lockTimeMinutes;
    }

    /**
     * @param account     로그인 하고자 하는 계정
     * @param currentTime 로그인 시도한 시간 로그인 실패 시, 호출해야하는 메소드입니다.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateLoginFailedInfo(final Account account, final LocalDateTime currentTime) {
        final Member member = findMember(account.getId());
        if (member.isOverMaxLoginTryCount(this.maxTryCount)) {
            member.lock(currentTime);
            log.info("Lock account {}, lock started at {}", account.getId(), currentTime);
        } else {
            member.increaseLoginTryCount();
            log.info("account {}, Increase login try count to {}", account.getId(), member.getLoginTryCount());
        }
    }

    /**
     * @param account 로그인 하고자 하는 대상입니다.
     * @return 로그인을 시도할 수 있다면 true, 아니라면 false
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public boolean checkAllowedToLogin(final Account account, final LocalDateTime currentTime) {
        final Member member = findMember(account.getId());
        return member.canLoginAt(currentTime, this.lockTimeMinutes);
    }

    @Transactional
    public void unlock(final Account account, final LocalDateTime currentTime) {
        final Member member = findMember(account.getId());
        unlockMember(member, currentTime);
    }

    private void unlockMember(final Member member, final LocalDateTime currentTime) {
        member.unlock();
        log.info("Check account {}, unlocked at {}", member.getId(), currentTime);
    }

    private Member findMember(final Long memberId) {
        return memberRepository.findMemberByIdAndRemovedIsFalse(memberId)
                .orElseThrow(() -> new NoSuchMemberException(ErrorCode.NO_SUCH_MEMBER));
    }
}
