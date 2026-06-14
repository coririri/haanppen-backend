package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.model.Account;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.security.Role;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(properties = {"login.lock.minutes=5", "login.lock.maxTryCount=3"})
class AccountLockServiceTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AccountLockService accountLockService;
    @Autowired
    private AccountLoader accountLoader;

    @Test
    @Transactional
    void 일정시간_이후_잠금해제_테스트() {
        final Member member = Member.builder()
                .phoneNumber("01011112222")
                .role(Role.TEACHER)
                .name("test")
                .encryptedPassword("tset")
                .build();
        memberRepository.save(member);

        member.lock(LocalDateTime.of(2020, 1, 1, 0, 0));
        final Account account = accountLoader.loadAccount(member.getId());

        LocalDateTime invalidTime = LocalDateTime.of(2020,1,1,0,5);
        LocalDateTime validTime = LocalDateTime.of(2020,1,1,0,6);
        Assertions.assertAll(
                () -> Assertions.assertEquals(accountLockService.checkAllowedToLogin(account, invalidTime), false),
                () -> Assertions.assertEquals(accountLockService.checkAllowedToLogin(account, validTime), true)
        );
    }
}