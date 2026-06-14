package com.hanpyeon.academyapi.course.application;

import com.hanpyeon.academyapi.course.application.dto.MemoRegisterCommand;
import com.hanpyeon.academyapi.course.application.exception.InvalidCourseAccessException;
import com.hanpyeon.academyapi.course.application.exception.NoSuchCourseException;
import com.hanpyeon.academyapi.course.application.port.out.LoadCourseTeacherIdPort;
import com.hanpyeon.academyapi.course.application.port.out.QueryMemoByCourseIdAndDatePort;
import com.hanpyeon.academyapi.course.application.port.out.RegisterMemoPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class MemoRegisterServiceTest {
    @Mock
    LoadCourseTeacherIdPort loadCourseTeacherIdPort;
    @Mock
    RegisterMemoPort registerMemoPort;
    @Mock
    QueryMemoByCourseIdAndDatePort queryMemoByCourseIdAndDatePort;
    @InjectMocks
    MemoRegisterService memoRegisterService;


    @Test
    @DisplayName("반 담당 선생 ID == 메모 등록 요청 맴버 ID")
    void legalMemoRegisterTest() {
        final long courseId = 2l;
        final long teacherId = 1l;

        Mockito.when(loadCourseTeacherIdPort.loadTeacherId(courseId))
                .thenReturn(teacherId);
        MemoRegisterCommand memoRegisterCommand = new MemoRegisterCommand(teacherId, courseId, "test", "test", LocalDate.now());

        assertThat(memoRegisterService.register(memoRegisterCommand))
                .isEqualTo(0l);
    }
    @Test
    @DisplayName("반 담당 선생 ID != 메모 등록 요청 맴버 ID")
    void illegalMemoRegisterTest() {
        final long courseId = 2l;
        final long teacherId = 1l;

        Mockito.when(loadCourseTeacherIdPort.loadTeacherId(courseId))
                .thenReturn(1234l);
        MemoRegisterCommand memoRegisterCommand = new MemoRegisterCommand(teacherId, courseId, "test", "test", LocalDate.now());

        assertThatThrownBy(() -> memoRegisterService.register(memoRegisterCommand))
                .isInstanceOf(InvalidCourseAccessException.class);
    }
}