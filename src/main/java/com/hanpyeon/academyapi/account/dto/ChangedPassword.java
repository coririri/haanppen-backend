package com.hanpyeon.academyapi.account.dto;

import com.hanpyeon.academyapi.account.model.Password;

public record ChangedPassword(
        String phoneNumber,
        Password changedPassword
) {
}
