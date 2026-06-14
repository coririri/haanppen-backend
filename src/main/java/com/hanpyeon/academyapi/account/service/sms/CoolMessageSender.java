package com.hanpyeon.academyapi.account.service.sms;

import com.hanpyeon.academyapi.account.dto.SendPasswordCommand;
import com.hanpyeon.academyapi.account.dto.SendValidationCodeCommand;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class CoolMessageSender implements MessageSender {

    private final DefaultMessageService defaultMessageService;
    private final MessageFactory messageFactory;

    @Override
    public void sendNewPassword(SendPasswordCommand sendPasswordCommand) {
        final MessageContent messageForm = new MessageContent("변경된 비밀번호 : " + sendPasswordCommand.getRawPassword() + "\n" + "비밀번호를 반드시 변경해주세요! :)");
        final SingleMessageSendingRequest singleMessageSendingRequest = messageFactory.getMessage(sendPasswordCommand.getTargetPhoneNumber().getPhoneNumber(), messageForm);
        defaultMessageService.sendOne(singleMessageSendingRequest);
    }

    @Override
    public void sendValidationCode(SendValidationCodeCommand sendValidationCodeCommand) {
        final MessageContent messageForm = new MessageContent("인증번호 : " + sendValidationCodeCommand.getValidationCode() + "\n" + " 현재 시도 횟수 : " + sendValidationCodeCommand.getCurrTryCount() + "/" + sendValidationCodeCommand.getMaxTryCount() + " :)");
        final SingleMessageSendingRequest singleMessageSendingRequest = messageFactory.getMessage(sendValidationCodeCommand.getTargetPhoneNumber().getPhoneNumber(), messageForm);
        defaultMessageService.sendOne(singleMessageSendingRequest);
    }
}
