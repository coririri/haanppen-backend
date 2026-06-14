package com.hanpyeon.academyapi.course.application;

import com.hanpyeon.academyapi.course.application.dto.RegisterStudentDto;
import com.hanpyeon.academyapi.course.application.port.in.AddStudentToCourseUseCase;
import com.hanpyeon.academyapi.course.application.port.out.AddCourseStudentPort;
import com.hanpyeon.academyapi.course.application.port.out.LoadCoursePort;
import com.hanpyeon.academyapi.course.application.port.out.LoadStudentsPort;
import com.hanpyeon.academyapi.course.domain.Course;
import com.hanpyeon.academyapi.course.domain.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class AddStudentToCourseService implements AddStudentToCourseUseCase {

    private final LoadCoursePort loadCoursePort;
    private final LoadStudentsPort loadStudentsPort;
    private final AddCourseStudentPort addCourseStudentPort;

    @Override
    public void addStudentToCourse(RegisterStudentDto registerStudentDto) {
        final Course course = loadCoursePort.loadCourse(registerStudentDto.courseId());
        final List<Student> students = loadStudentsPort.loadStudents(registerStudentDto.targetMembersId());

        course.addStudents(students);
        addCourseStudentPort.addToCourse(course);
    }
}
