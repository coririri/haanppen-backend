package com.hanpyeon.academyapi.course.application;

import com.hanpyeon.academyapi.course.application.dto.CourseRegisterDto;
import com.hanpyeon.academyapi.course.application.port.out.LoadStudentsPort;
import com.hanpyeon.academyapi.course.application.port.out.LoadTeacherPort;
import com.hanpyeon.academyapi.course.application.port.out.RegisterCoursePort;
import com.hanpyeon.academyapi.course.domain.Student;
import com.hanpyeon.academyapi.course.domain.Teacher;
import com.hanpyeon.academyapi.security.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CourseRegisterServiceTest {

    @Mock
    LoadStudentsPort loadStudentsPort;
    @Mock
    LoadTeacherPort loadTeacherPort;
    @Mock
    RegisterCoursePort registerCoursePort;

    @InjectMocks
    CourseRegisterService courseRegisterService;

    @Test
    void 등록_테스트() {
        CourseRegisterDto courseRegisterDto = new CourseRegisterDto("1", 1l, null, 1l, Role.TEACHER);


        Mockito.when(registerCoursePort.register(Mockito.any()))
                .thenReturn(1l);
        Mockito.when(loadStudentsPort.loadStudents(Mockito.any()))
                .thenReturn(List.of(new Student(1l, "test", 12)));
        Mockito.when(loadTeacherPort.loadTeacher(Mockito.any()))
                .thenReturn(new Teacher(1l, "test"));

        assertThat(courseRegisterService.register(courseRegisterDto))
                .isEqualTo(1l);
    }
}