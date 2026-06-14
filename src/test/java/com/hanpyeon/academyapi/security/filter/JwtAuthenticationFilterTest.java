package com.hanpyeon.academyapi.security.filter;

import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.security.JwtUtils;
import com.hanpyeon.academyapi.security.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class JwtAuthenticationFilterTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ApplicationContext context;

    @Autowired
    JwtUtils jwtUtils;

    @Test
    void 잘못된_JWT_테스트() throws Exception {
        mockMvc.perform(post("/api/accounts").header(JwtUtils.HEADER, JwtUtils.TOKEN_TYPE + " " + "rfeefew"))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(containsString(ErrorCode.AUTHENTICATION_FAILED_EXCEPTION.getErrorCode())));
    }

    @Test
    void 조작된_JWT_테스트() throws Exception {
        String testToken = jwtUtils.generateAccessToken((long) 12, Role.STUDENT, null) + "12";
        mockMvc.perform(get("/any").header(JwtUtils.HEADER, testToken))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(containsString(ErrorCode.AUTHENTICATION_FAILED_EXCEPTION.getErrorCode())));
    }

    @Test
    void 부적절한_JWT_테스트() throws Exception {
        String testToken = jwtUtils.generateAccessToken((long) 12, Role.STUDENT, null);
        mockMvc.perform(get("/any").header(JwtUtils.HEADER, testToken))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(containsString(ErrorCode.AUTHENTICATION_FAILED_EXCEPTION.getErrorCode())));
    }
}