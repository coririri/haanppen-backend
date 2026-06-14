package com.hanpyeon.academyapi.account.service.sms;

import com.hanpyeon.academyapi.account.dto.SendPasswordCommand;
import com.hanpyeon.academyapi.account.dto.SendValidationCodeCommand;

public interface MessageSender {
    void sendNewPassword(final SendPasswordCommand sendPasswordCommand);
    void sendValidationCode(final SendValidationCodeCommand sendValidationCodeCommand);
}
