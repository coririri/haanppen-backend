package com.hanpyeon.academyapi.course.adapter.in;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanpyeon.academyapi.course.application.port.in.CourseRegisterUseCase;
import com.hanpyeon.academyapi.security.Role;
import com.hanpyeon.academyapi.security.authentication.JwtAuthenticationToken;
import com.hanpyeon.academyapi.security.authentication.MemberPrincipal;
import com.hanpyeon.academyapi.security.filter.JwtAuthenticationFilter;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(controllers = RegisterCourseController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class, // 추가
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SecurityConfig.class, JwtAuthenticationFilter.class})
        })
class CourseRegisterControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    CourseRegisterUseCase courseRegisterUseCase;

    @BeforeEach
    void init() {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(JwtAuthenticationToken.authenticated("test", new MemberPrincipal(1l, "test", Role.STUDENT), null));
    }

    @Test
    void 반_등록_테스트() throws Exception {

        Mockito.when(courseRegisterUseCase.register(Mockito.any()))
                .thenReturn(1l);

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/manage/courses")
                        .content(objectMapper.writeValueAsBytes(new RegisterCourseController.CourseRegisterRequestDto("12", 1l, List.of(1l))))
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andDo(print());
    }
}