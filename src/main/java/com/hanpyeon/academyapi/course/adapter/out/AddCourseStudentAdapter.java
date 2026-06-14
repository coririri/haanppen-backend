package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.course.application.exception.NoSuchCourseException;
import com.hanpyeon.academyapi.course.application.exception.NoSuchMemberException;
import com.hanpyeon.academyapi.course.application.port.out.AddCourseStudentPort;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class AddCourseStudentAdapter implements AddCourseStudentPort {

    private final MemberRepository memberRepository;
    private final CourseRepository courseRepository;
    private final CourseStudentRepository courseStudentRepository;

    @Override
    public void addToCourse(final com.hanpyeon.academyapi.course.domain.Course courseDomain) {
        final List<Member> students = memberRepository.findAllById(
                courseDomain.getStudents().stream()
                        .map(student -> student.id())
                        .toList());
        if (students.size() != courseDomain.getStudents().size()) {
            throw new NoSuchMemberException("새로 등록될 학생을 찾을 수 없습니다", ErrorCode.NO_SUCH_COURSE_MEMBER);
        }
        final Course course = courseRepository.findById(courseDomain.getCourseId())
                .orElseThrow(() -> new NoSuchCourseException(ErrorCode.NO_SUCH_COURSE));

        courseStudentRepository.saveAll(students.stream()
                .map(student -> CourseStudent.addToCourse(student, course))
                .toList());
    }
}
