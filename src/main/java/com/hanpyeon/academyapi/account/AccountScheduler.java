package com.hanpyeon.academyapi.account;

import com.hanpyeon.academyapi.account.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountScheduler {
    private final MemberRepository memberRepository;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void resetVerificationStatus() {
        memberRepository.findAll().stream()
                .forEach(member -> member.resetVerifyInfo());
    }
}
