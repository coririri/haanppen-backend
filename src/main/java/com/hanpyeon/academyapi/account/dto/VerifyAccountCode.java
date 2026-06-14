package com.hanpyeon.academyapi.account.dto;

public record VerifyAccountCode (
        String phoneNumber,
        String verificationCode
){
}
