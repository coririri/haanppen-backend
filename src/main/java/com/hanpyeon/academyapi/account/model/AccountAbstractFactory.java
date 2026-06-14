package com.hanpyeon.academyapi.account.model;

import com.hanpyeon.academyapi.security.PasswordHandler;
import com.hanpyeon.academyapi.security.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountAbstractFactory {
    private final PasswordHandler passwordHandler;

    public AccountGrade getGrade(final Integer grade){
        return AccountGrade.of(grade);
    }

    public AccountPassword getPasswordFromEntity(final String encryptedPassword) {
        return AccountPassword.load(encryptedPassword, passwordHandler);
    }

    public AccountPassword getPassword(final Password rawPassword) {
        return AccountPassword.createNew(rawPassword, passwordHandler);
    }

    public AccountName getName(final String name) {
        return AccountName.of(name);
    }

    public AccountPhoneNumber getPhoneNumber(final String phoneNumber) {
        return AccountPhoneNumber.of(phoneNumber);
    }

    public AccountRole getAccountRole(final Role role) {
        return AccountRole.of(role);
    }
}
