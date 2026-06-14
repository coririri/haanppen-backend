package com.hanpyeon.academyapi.course.application;

import com.hanpyeon.academyapi.course.application.dto.UpdateCourseStudentsDto;
import com.hanpyeon.academyapi.course.application.port.in.UpdateCourseStudentsUseCase;
import com.hanpyeon.academyapi.course.application.port.out.LoadCoursePort;
import com.hanpyeon.academyapi.course.application.port.out.LoadStudentsPort;
import com.hanpyeon.academyapi.course.application.port.out.UpdateCoursePort;
import com.hanpyeon.academyapi.course.domain.Course;
import com.hanpyeon.academyapi.course.domain.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UpdateCourseStudentsService implements UpdateCourseStudentsUseCase {
    private final LoadStudentsPort loadStudentsPort;
    private final LoadCoursePort loadCoursePort;
    private final UpdateCoursePort updateCoursePort;

    @Transactional
    public void updateStudents(UpdateCourseStudentsDto updateCourseStudentsDto) {
        final List<Student> students = loadStudentsPort.loadStudents(updateCourseStudentsDto.studentIds());
        final Course targetCourse = loadCoursePort.loadCourse(updateCourseStudentsDto.courseId());
        targetCourse.setStudents(students);
        updateCoursePort.updateCourse(targetCourse);
    }
}
