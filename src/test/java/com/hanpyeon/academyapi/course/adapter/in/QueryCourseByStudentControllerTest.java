package com.hanpyeon.academyapi.course.adapter.in;

import com.hanpyeon.academyapi.course.application.LoadCoursesByStudentQueryService;
import com.hanpyeon.academyapi.security.filter.JwtAuthenticationFilter;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(controllers = QueryCourseByStudentController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class, // 추가
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SecurityConfig.class, JwtAuthenticationFilter.class})
        })
class QueryCourseByStudentControllerTest {

    @MockBean
    LoadCoursesByStudentQueryService loadCoursesByStudentQueryService;

    @Autowired
    MockMvc mockMvc;

    @Test
    void 요청_성공테스트() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/courses/students/{studentId}", 1l))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}