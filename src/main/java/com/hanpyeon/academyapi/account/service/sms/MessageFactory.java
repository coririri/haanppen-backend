package com.hanpyeon.academyapi.account.service.sms;

import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class MessageFactory {

    private final String sender;

    public MessageFactory(@Value("${spring.sms.coolsms.sender}") final String sender) {
        this.sender = sender;
    }

    SingleMessageSendingRequest getMessage(final String targetPhoneNumber, final MessageContent messageForm) {
        final Message message = new Message();
        message.setFrom(sender);
        message.setTo(targetPhoneNumber);
        message.setText(messageForm.toString());
        return new SingleMessageSendingRequest(message);
    }
}
