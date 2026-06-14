package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.dto.AccountUpdateCommand;
import com.hanpyeon.academyapi.account.dto.MyAccountInfo;
import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.model.AccountAbstractFactory;
import com.hanpyeon.academyapi.account.model.AccountGrade;
import com.hanpyeon.academyapi.account.model.AccountName;
import com.hanpyeon.academyapi.account.model.AccountPassword;
import com.hanpyeon.academyapi.account.model.AccountPhoneNumber;
import com.hanpyeon.academyapi.account.model.Password;
import com.hanpyeon.academyapi.account.model.ResetAccountPassword;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.security.PasswordHandler;
import com.hanpyeon.academyapi.security.Role;
import java.time.LocalDateTime;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@ActiveProfiles("test")
class AccountUpdateServiceTest {
    final static String prevPassword = "fmeqkrlfmqergkmmrk";
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    AccountUpdateService accountUpdateService;
    @Autowired
    AccountLoader accountLoader;
    @Autowired
    private PasswordHandler passwordHandler;
    @Autowired
    private AccountAbstractFactory accountAbstractFactory;


    @ParameterizedTest
    @MethodSource("provideLegalArguments")
    @Transactional
    void testModifyCommand(final String phoneNumber, final String newName, final int newGrade, final String prevPassword, final String nextPassword) {
        final Long targetMemberId = initData();
        final AccountUpdateCommand accountUpdateCommand = new AccountUpdateCommand(targetMemberId, AccountPhoneNumber.of(phoneNumber),
                AccountName.of(newName),
                AccountGrade.of(newGrade),
                ResetAccountPassword.of(new Password(prevPassword), AccountPassword.createNew(new Password(nextPassword), passwordHandler)));
        accountUpdateService.updateAccount(accountUpdateCommand);
        final MyAccountInfo accountInfo = accountUpdateService.getMyInfo(accountUpdateCommand.targetMemberId());
        System.out.println(accountInfo);
        Assertions.assertThat(accountInfo.userName().equals(accountUpdateCommand.name()));
        Assertions.assertThat(accountInfo.phoneNumber().equals(accountUpdateCommand.phoneNumber()));
        Assertions.assertThat(accountInfo.grade().equals(accountUpdateCommand.grade()));
    }

    static Stream<Arguments> provideLegalArguments() {
        return Stream.of(
                Arguments.of("01022222222", "tesst", 1, prevPassword, ""),
                Arguments.of("01022222222", "tesst", 1, prevPassword, "test"),
                Arguments.of(null, "tesst", 1, prevPassword, "test"),
                Arguments.of("01012345678", null, 0, prevPassword, "test"),
                Arguments.of("01012345678", "tesst", 11, null, null)
        );
    }

    @Transactional
    @MethodSource("provideIllegalArguments")
    @ParameterizedTest
    void testWrongModifyCommand(final String phoneNumber, final String newName, final int newGrade, final String prevPassword, final String nextPassword) {
        final Long targetMemberId = initData();
        Assertions.assertThatThrownBy(() ->
        new AccountUpdateCommand(targetMemberId, AccountPhoneNumber.of(phoneNumber),
                AccountName.of(newName),
                AccountGrade.of(newGrade),
                ResetAccountPassword.of(new Password(prevPassword), AccountPassword.createNew(new Password(nextPassword), passwordHandler)))
        );
    }

    static Stream<Arguments> provideIllegalArguments() {
        return Stream.of(
                Arguments.of("010222222222", "tesst", 1, prevPassword, "test"),
                Arguments.of("0102222222", "tesst", 1, prevPassword, "test"),
                Arguments.of("01012345678", "tesstqwedwqewqeqweqw", 1, prevPassword, "test"),
                Arguments.of("01012345678", "tesst", -1, "test", "test"),
                Arguments.of("01012345678", "tesst", 12, "test", "test")
        );
    }

    Long initData() {
        final AccountPassword accountPassword = accountAbstractFactory.getPassword(new Password(prevPassword));
        final Member member = Member.builder()
                .role(Role.STUDENT)
                .phoneNumber("01011111222")
                .grade(1)
                .registeredDate(LocalDateTime.now())
                .name("testqwe")
                .encryptedPassword(accountPassword.getEncryptedPassword())
                .build();
        return memberRepository.save(member).getId();
    }
}