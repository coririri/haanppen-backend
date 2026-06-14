package com.hanpyeon.academyapi.account.service.sms;

class MessageContent {
    private final String contentPrefix = "[한편의 수학]";
    private final String content;

    public MessageContent(final String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return contentPrefix + " " + content;
    }
}
