package com.hanpyeon.academyapi.account.service.policy;

import com.hanpyeon.academyapi.account.model.Account;

interface AccountPolicy {
    void verify(final Account account);
}
