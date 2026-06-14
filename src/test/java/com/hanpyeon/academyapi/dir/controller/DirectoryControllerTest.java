package com.hanpyeon.academyapi.dir.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanpyeon.academyapi.dir.service.DirectoryService;
import com.hanpyeon.academyapi.security.JwtUtils;
import com.hanpyeon.academyapi.security.Role;
import com.hanpyeon.academyapi.security.SecurityConfig;
import com.hanpyeon.academyapi.security.filter.JwtAuthenticationFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DirectoryControllerTest {

    @MockBean
    DirectoryService directoryService;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    AuthenticationManager authenticationManager;
    @ParameterizedTest
    @MethodSource("provideLegalRoles")
    void 디렉토리생성_권한_성공테스트(final Role role) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/directories")
                        .header(JwtUtils.HEADER, jwtUtils.generateAccessToken(1l, role, "test"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new DirectoryController.CreateDirectoryRequest("/test", "test", false, false))))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void 디렉토리생성_권한_실패테스트() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.post("/api/directories")
                        .header(JwtUtils.HEADER, jwtUtils.generateAccessToken(1l, Role.STUDENT, "test"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new DirectoryController.CreateDirectoryRequest("test", "test", false, false))))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    static Stream<Arguments> provideLegalRoles() {
        return Stream.of(Arguments.of(Role.MANAGER),
                Arguments.of(Role.ADMIN),
                Arguments.of(Role.TEACHER));
    }
}