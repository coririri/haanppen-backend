package com.hanpyeon.academyapi.account.entity;

import com.hanpyeon.academyapi.account.model.Password;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.account.model.Account;
import com.hanpyeon.academyapi.account.model.AccountAbstractFactory;
import com.hanpyeon.academyapi.account.model.AccountPassword;
import com.hanpyeon.academyapi.security.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@SpringBootTest
@ActiveProfiles("test")
class AccountApplierTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private AccountAbstractFactory accountAbstractFactory;
    @Autowired
    private AccountApplier accountApplier;


    @Transactional
    @MethodSource
    @Test
    void testApplyingAccount() {
        final Long targetMemberId = initMember();
        final AccountPassword accountPassword = accountAbstractFactory.getPassword(new Password("testPasword"));

        final Account account = Account.of(
                targetMemberId,
                accountAbstractFactory.getPhoneNumber("01001010101"),
                accountAbstractFactory.getName("test"),
                accountAbstractFactory.getAccountRole(Role.STUDENT),
                accountAbstractFactory.getGrade(1),
                accountPassword

        );
        accountApplier.applyAccount(account);

        final Member targetMember = memberRepository.findMemberByIdAndRemovedIsFalse(targetMemberId)
                .orElseThrow();
        Assertions.assertThat(targetMember.getPhoneNumber()).isEqualTo("01001010101");
        Assertions.assertThat(targetMember.getName()).isEqualTo("test");
        Assertions.assertThat(targetMember.getRole()).isEqualTo(Role.STUDENT);
        Assertions.assertThat(targetMember.getGrade()).isEqualTo(1);
        Assertions.assertThat(targetMember.getPhoneNumber()).isEqualTo("01001010101");
        Assertions.assertThat(accountPassword.isMatch(targetMember.getPassword()));
    }


    Long initMember() {
        final Member member = Member.builder()
                .role(Role.STUDENT)
                .phoneNumber("01000000000")
                .grade(1)
                .registeredDate(LocalDateTime.now())
                .name("test")
                .encryptedPassword("teqdwdwqqrek;fmeqkrlfmqergkmmrk")
                .build();
        return memberRepository.save(member).getId();
    }
}