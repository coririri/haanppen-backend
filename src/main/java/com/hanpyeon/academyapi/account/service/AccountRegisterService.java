package com.hanpyeon.academyapi.account.service;

import com.hanpyeon.academyapi.account.dto.RegisterMemberCommand;
import com.hanpyeon.academyapi.account.entity.AccountApplier;
import com.hanpyeon.academyapi.account.entity.AccountMapper;
import com.hanpyeon.academyapi.account.model.Account;
import com.hanpyeon.academyapi.account.service.policy.AccountPolicyManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AccountRegisterService {
    private final AccountMapper accountMapper;
    private final AccountPolicyManager accountPolicyManager;
    private final AccountApplier accountApplier;

    @Transactional
    public void register(final RegisterMemberCommand registerMemberDto) {
        final Account account = accountMapper.mapToAccount(registerMemberDto);
        accountPolicyManager.check(account);
        accountApplier.save(account);
    }
}
