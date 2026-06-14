package com.hanpyeon.academyapi.account;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class AccountConfig {
    @Bean
    Random random() {
        return new Random();
    }
}
