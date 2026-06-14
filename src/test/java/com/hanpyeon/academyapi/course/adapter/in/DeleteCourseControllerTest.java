package com.hanpyeon.academyapi.course.adapter.in;

import com.hanpyeon.academyapi.course.application.port.in.DeleteCourseUseCase;
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

@WebMvcTest(controllers = DeleteCourseController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class, // 추가
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SecurityConfig.class, JwtAuthenticationFilter.class})
        })
class DeleteCourseControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    DeleteCourseUseCase deleteCourseAdapter;

    @Test
    void test() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/manage/courses/{courseId}", 1))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}