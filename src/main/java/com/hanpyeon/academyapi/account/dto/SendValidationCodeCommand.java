package com.hanpyeon.academyapi.account.dto;

import com.hanpyeon.academyapi.account.model.AccountPhoneNumber;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class SendValidationCodeCommand {
    private final AccountPhoneNumber targetPhoneNumber;
    private final String validationCode;
    private final Integer currTryCount;
    private final Integer maxTryCount;
    private final Integer validTime;
}
