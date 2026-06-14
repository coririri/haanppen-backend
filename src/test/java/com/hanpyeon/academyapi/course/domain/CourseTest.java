package com.hanpyeon.academyapi.course.domain;

import com.hanpyeon.academyapi.course.application.exception.IllegalCourseNameException;
import com.hanpyeon.academyapi.course.application.exception.IllegalCourseStudentStateException;
import com.hanpyeon.academyapi.course.application.exception.NotFoundTeacherException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CourseTest {

    @Test
    void Course_생성_성공_테스트() {
        final String name = "1212";
        final List<Student> student = List.of(new Student(1l, "test", 12));
        final Teacher teacher = Mockito.mock(Teacher.class);

        assertDoesNotThrow(() -> Course.createNewCourse(name, student, teacher));
    }

    @Test
    void Course_제목_글자수_초과() {
        final String name = "1".repeat(101);
        final List<Student> students = List.of(new Student(1l, "test", 12));
        final Teacher teacher = new Teacher(2l, "test");

        assertThatThrownBy(() -> Course.createNewCourse(name, students, teacher))
                .isInstanceOf(IllegalCourseNameException.class);
    }
    @Test
    void Course_학생_상태_이상() {
        final String name = "1".repeat(10);
        final List<Student> students = new ArrayList<>();
        students.add(new Student(1l, "test", 12));
        students.add(null);
        final Teacher teacher = new Teacher(2l, "test");

        assertThatThrownBy(() -> Course.createNewCourse(name, students, teacher))
                .isInstanceOf(IllegalCourseStudentStateException.class);
    }
    @Test
    void Course_선생_없음() {
        final String name = "1".repeat(10);
        final List<Student> students = List.of(new Student(1l, "test", 12));
        final Teacher teacher = null;

        assertThatThrownBy(() -> Course.createNewCourse(name, students, teacher))
                .isInstanceOf(NotFoundTeacherException.class);
    }

    @Test
    void 중복학생처리_테스트() {
        List<Student> students = new ArrayList<>(List.of(new Student(1l, "test", 12), new Student(2l, "test", 12)));
        List<Student> newStudents = new ArrayList<>(List.of(new Student(1l, "test", 12), new Student(2l, "test", 12)));

        Course course = Course.loadByEntity(1l, "temp", students, new Teacher(10l, "test"));
        course.addStudents(newStudents);
        assertThat(course.getStudents().size())
                .isEqualTo(2);

    }

}