package com.hanpyeon.academyapi.account.dto;

import com.hanpyeon.academyapi.security.Role;

public record MyAccountInfo (
        String userName,
        String phoneNumber,
        Role role,
        Integer grade
){
}
