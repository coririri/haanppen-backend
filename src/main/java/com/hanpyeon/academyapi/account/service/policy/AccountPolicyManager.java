package com.hanpyeon.academyapi.account.service.policy;

import com.hanpyeon.academyapi.account.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountPolicyManager {
    private final List<AccountPolicy> policies;

    public void check(final Account account) {
        policies.stream()
                .forEach(policy -> policy.verify(account));
    }
}
