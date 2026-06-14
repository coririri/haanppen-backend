package com.hanpyeon.academyapi.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanpyeon.academyapi.account.service.AccountPasswordRefreshService;
import com.hanpyeon.academyapi.account.service.AccountRegisterService;
import com.hanpyeon.academyapi.account.service.AccountRemoveService;
import com.hanpyeon.academyapi.account.service.AccountUpdateService;
import com.hanpyeon.academyapi.security.PasswordHandler;
import com.hanpyeon.academyapi.security.filter.JwtAuthenticationFilter;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AccountController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class, // 추가
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SecurityConfig.class, JwtAuthenticationFilter.class})
        })
public class AccountControllerTest {
    private static final String BASE_URL = "/api/accounts";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    AccountUpdateService accountUpdateService;
    @MockBean
    AccountRemoveService accountRemoveService;
    @MockBean
    AccountRegisterService accountRegisterService;
    @MockBean
    AccountPasswordRefreshService accountPasswordRefreshService;
    @MockBean
    PasswordHandler passwordHandler;

    @ParameterizedTest
    @MethodSource("provideIllegalArguments")
    void 잘못된_요청_테스트(Map<String, Object> requestDto) throws Exception {
        request(status().isBadRequest(), requestDto);
    }

    @ParameterizedTest
    @MethodSource("provideLegalArguments")
    void 옳은_요청_테스트(Map<String, Object> requestDto) throws Exception {
        request(status().isCreated(), requestDto);
    }

    private void request(ResultMatcher resultMatcher, Map<String, Object> requestDto) throws Exception {
        mockMvc.perform(post(BASE_URL)
                .content(objectMapper.writeValueAsString(requestDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(csrf())
        ).andExpect(resultMatcher);
    }

    private static Map<String, Object> createRequest(String name, Integer grade, String phoneNumber, String role, String password) {
        Map<String, Object> body = new HashMap<>();
        body.put("name", name);
        body.put("grade", grade);
        body.put("phoneNumber", phoneNumber);
        body.put("role", role);
        body.put("password", password);
        return body;
    }

    public static Stream<Arguments> provideLegalArguments() {
        return Stream.of(
                Arguments.of(createRequest("희종", 0, "102010021", "student", null)),
                Arguments.of(createRequest("TOM", 11, "102010021", "student", null)),
                Arguments.of(createRequest("COKE", 10, "1022", "manager", null)),
                Arguments.of(createRequest("COKE", null, "1022", "manager", null))
        );
    }

    public static Stream<Arguments> provideIllegalArguments() {
        return Stream.of(
                Arguments.of(createRequest("Tom", null, null, "student", null)),
                Arguments.of(createRequest(null, 10, "010101001", "student", null)),
                Arguments.of(createRequest("Tom", null, "010101001", null, null)),
                Arguments.of(createRequest("Tom", 10, null, null, null)),

                Arguments.of(createRequest("Heejong", 10, "0101010101ㅂㅈㄱㄷ", null, null)),
                Arguments.of(createRequest("Tom", 10, "ewfjnede", "student", null)),
                Arguments.of(createRequest("희종", 12, "10100101001", null, null)),
                Arguments.of(createRequest("희종", -1, "10100101001", "student", null))
        );
    }
}