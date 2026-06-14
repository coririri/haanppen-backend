package com.hanpyeon.academyapi.account.entity;

import com.hanpyeon.academyapi.account.dto.RegisterMemberCommand;
import com.hanpyeon.academyapi.account.model.Account;
import com.hanpyeon.academyapi.account.model.AccountAbstractFactory;
import com.hanpyeon.academyapi.account.model.AccountGrade;
import com.hanpyeon.academyapi.account.model.AccountName;
import com.hanpyeon.academyapi.account.model.AccountPhoneNumber;
import com.hanpyeon.academyapi.account.model.AccountRole;
import com.hanpyeon.academyapi.account.model.AccountPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountMapper {
    private final AccountAbstractFactory accountAbstractFactory;

    public Account mapToAccount(final Member memberEntity) {
        final AccountGrade accountGrade = accountAbstractFactory.getGrade(memberEntity.getGrade());
        final AccountRole accountRole = accountAbstractFactory.getAccountRole(memberEntity.getRole());
        final AccountName accountName = accountAbstractFactory.getName(memberEntity.getName());
        final AccountPhoneNumber accountPhoneNumber = accountAbstractFactory.getPhoneNumber(memberEntity.getPhoneNumber());
        final AccountPassword password = accountAbstractFactory.getPasswordFromEntity(memberEntity.getPassword());

        return Account.of(memberEntity.getId(), accountPhoneNumber, accountName, accountRole, accountGrade, password);
    }

    public Account mapToAccount(final RegisterMemberCommand registerMemberDto) {
        final AccountGrade accountGrade = accountAbstractFactory.getGrade(registerMemberDto.grade());
        final AccountRole accountRole = accountAbstractFactory.getAccountRole(registerMemberDto.role());
        final AccountName accountName = accountAbstractFactory.getName(registerMemberDto.name());
        final AccountPhoneNumber accountPhoneNumber = accountAbstractFactory.getPhoneNumber(registerMemberDto.phoneNumber());
        final AccountPassword password = accountAbstractFactory.getPassword(registerMemberDto.password());

        return Account.of(accountPhoneNumber, accountName, accountRole, accountGrade, password);
    }

    Member mapToMember(final Account account) {
        return Member.builder()
                .encryptedPassword(account.getPassword().getEncryptedPassword())
                .role(account.getAccountRole().getRole())
                .grade(account.getGrade() == null ? null : account.getGrade().getGrade())
                .name(account.getAccountName().getName())
                .phoneNumber(account.getPhoneNumber().getPhoneNumber())
                .build();
    }
}
