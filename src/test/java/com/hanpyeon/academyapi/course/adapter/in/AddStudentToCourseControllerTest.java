package com.hanpyeon.academyapi.course.adapter.in;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hanpyeon.academyapi.course.application.port.in.AddStudentToCourseUseCase;
import com.hanpyeon.academyapi.security.filter.JwtAuthenticationFilter;
import org.apache.catalina.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@WebMvcTest(controllers = AddStudentToCourseController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class, // 추가
        excludeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {SecurityConfig.class, JwtAuthenticationFilter.class})
        })
class AddStudentToCourseControllerTest {
        @Autowired
        MockMvc mockMvc;
        @MockBean
        AddStudentToCourseUseCase addStudentToCourseUseCase;

        @Autowired
        ObjectMapper objectMapper;
        @Test
        void 학생등록요청_테스트() throws Exception {
                AddStudentToCourseController.RegisterStudentRequest request = new AddStudentToCourseController.RegisterStudentRequest(1l, List.of(2l,3l));
                mockMvc.perform(MockMvcRequestBuilders.post("/api/manage/courses/students")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsBytes(request))
                ).andExpect(MockMvcResultMatchers.status().isOk())
                        .andDo(MockMvcResultHandlers.print());
        }
        @Test
        void 학생_NULL_테스트_에러처리() throws Exception {
                AddStudentToCourseController.RegisterStudentRequest request = new AddStudentToCourseController.RegisterStudentRequest(1l, null);
                mockMvc.perform(MockMvcRequestBuilders.post("/api/manage/courses/students")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsBytes(request))
                ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andDo(MockMvcResultHandlers.print());
        }
        @Test
        void 등록할_반_NULL_에러처리() throws Exception {
                AddStudentToCourseController.RegisterStudentRequest request = new AddStudentToCourseController.RegisterStudentRequest(null, List.of(1l));
                mockMvc.perform(MockMvcRequestBuilders.post("/api/manage/courses/students")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsBytes(request))
                        ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                        .andDo(MockMvcResultHandlers.print());
        }
}