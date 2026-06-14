package com.hanpyeon.academyapi.account.service.sms;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class SmsConfiguration {

    private final String apiKey;
    private final String apiSecret;
    private final String smsProvider;
    private final String smsSender;
    public SmsConfiguration(
            @Value("${spring.sms.coolsms.api-key}") String apiKey,
            @Value("${spring.sms.coolsms.api-secret}") String apiSecret,
            @Value("${spring.sms.coolsms.provider}") String smsProvider,
            @Value("${spring.sms.coolsms.sender}") String smsSender
    ) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.smsProvider = smsProvider;
        this.smsSender = smsSender;
    }
    @Bean
    public DefaultMessageService defaultMessageService() {
        return NurigoApp.INSTANCE.initialize(
                apiKey,
                apiSecret,
                smsProvider
        );
    }
}
