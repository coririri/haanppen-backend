package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.dto.AccountUpdateCommand;
import com.hanpyeon.academyapi.account.dto.MyAccountInfo;
import com.hanpyeon.academyapi.account.entity.AccountApplier;
import com.hanpyeon.academyapi.account.model.Account;
import com.hanpyeon.academyapi.account.model.AccountGrade;
import com.hanpyeon.academyapi.account.model.AccountName;
import com.hanpyeon.academyapi.account.model.AccountPhoneNumber;
import com.hanpyeon.academyapi.account.model.ResetAccountPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
public class AccountUpdateService {
    private final AccountLoader accountLoader;
    private final AccountApplier accountApplier;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void updateAccount(@Validated final AccountUpdateCommand updateCommand) {
        final Account targetAccount = accountLoader.loadAccount(updateCommand.targetMemberId());
        updatePhoneNumber(updateCommand.phoneNumber(), targetAccount);
        updateGrade(updateCommand.grade(), targetAccount);
        updateName(updateCommand.name(), targetAccount);
        updatePassword(updateCommand.resetAccountPassword(), targetAccount);
        accountApplier.applyAccount(targetAccount);
    }

    private void updatePassword(final ResetAccountPassword resetAccountPassword, final Account account) {
        if (resetAccountPassword == null) {
            return;
        }
        account.updatePassword(resetAccountPassword);
    }

    private void updateName(final AccountName accountName, final Account targetAccount) {
        if (accountName != null) {
            targetAccount.updateAccountName(accountName);
        }
    }

    private void updateGrade(final AccountGrade accountGrade, final Account targetAccount) {
        if (accountGrade != null) {
            targetAccount.updateGrade(accountGrade);
        }
    }

    private void updatePhoneNumber(final AccountPhoneNumber accountPhoneNumber, final Account targetAccount) {
        if (accountPhoneNumber != null) {
            targetAccount.setPhoneNumber(accountPhoneNumber);
        }
    }

    @Transactional(readOnly = true)
    public MyAccountInfo getMyInfo(final Long requestMemberId) {
        final Account account = accountLoader.loadAccount(requestMemberId);
        return new MyAccountInfo(
                account.getAccountName().getName(),
                account.getPhoneNumber().getPhoneNumber(),
                account.getAccountRole().getRole(),
                account.getGrade() == null ? null : account.getGrade().getGrade()
        );
    }
}
