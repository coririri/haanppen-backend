package com.hanpyeon.academyapi.account.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Account {
    private final Long id;
    private AccountPhoneNumber phoneNumber;
    private AccountName accountName;
    private AccountRole accountRole;
    private AccountGrade grade;
    private AccountPassword password;

    public boolean isMatchPassword(final Password password) {
        return this.password.isMatch(password.getPassword());
    }

    public void updateAccountName(AccountName accountName) {
        this.accountName = accountName;
    }

    public void updateGrade(AccountGrade grade) {
        this.grade = grade;
        grade.validate(accountRole);
    }

    public void updatePassword(final ResetAccountPassword resetAccountPassword) {
        resetAccountPassword.isMatchToPrevPassword(password);
        this.password = resetAccountPassword.getNewPassword();
    }

    public void setPhoneNumber(AccountPhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public static Account of(final AccountPhoneNumber phoneNumber, final AccountName name, final AccountRole role, final AccountGrade grade, final AccountPassword password) {
        return new Account(null, phoneNumber, name, role, grade, password);
    }

    public static Account of(final Long accountId, final AccountPhoneNumber phoneNumber, final AccountName name, final AccountRole role, final AccountGrade grade, final AccountPassword password) {
        return new Account(accountId, phoneNumber, name, role, grade, password);
    }
}
