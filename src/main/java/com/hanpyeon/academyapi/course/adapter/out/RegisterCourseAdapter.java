package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.course.application.port.out.RegisterCoursePort;
import com.hanpyeon.academyapi.course.domain.Student;
import com.hanpyeon.academyapi.course.domain.Teacher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class RegisterCourseAdapter implements RegisterCoursePort {
    private final CourseRepository courseRepository;
    private final CourseStudentRepository courseStudentRepository;
    private final MemberRepository memberRepository;

    @Override
    public Long register(final com.hanpyeon.academyapi.course.domain.Course course) {
        Course courseEntity = mapToEntity(course);
        final List<CourseStudent> courseStudents = createCourseStudents(
                findStudents(course.getStudents()),
                courseEntity);
        // 연관관계 주인 엔티티에 추가하도록 변경
        final Long courseId = courseRepository.save(courseEntity).getId();
        courseStudentRepository.saveAll(courseStudents);
        return courseId;
    }

    private Course mapToEntity(final com.hanpyeon.academyapi.course.domain.Course course) {
        return new Course(
                course.getCourseName(),
                findTeacher(course.getTeacher())
        );
    }

    private List<CourseStudent> createCourseStudents(final List<Member> students, final Course courseEntity) {
        return students.stream()
                .map(student -> CourseStudent.addToCourse(student, courseEntity))
                .toList();
    }

    private List<Member> findStudents(final List<Student> students) {
        List<Long> studentIds = students.stream()
                .map(student -> student.id())
                .toList();
        return memberRepository.findAllById(studentIds);
    }

    private Member findTeacher(final Teacher teacher) {
        return memberRepository.findById(teacher.id())
                .orElseThrow();
    }
}
