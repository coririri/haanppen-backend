package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.course.application.port.out.LoadCoursePort;
import com.hanpyeon.academyapi.course.application.port.out.RegisterCoursePort;
import com.hanpyeon.academyapi.course.domain.Student;
import com.hanpyeon.academyapi.security.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({AddCourseStudentAdapter.class, RegisterCourseAdapter.class, LoadCourseAdapter.class, CourseMapper.class})
class AddCourseStudentAdapterTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    CourseStudentRepository courseStudentRepository;
    @Autowired
    RegisterCoursePort registerCoursePort;

    @Autowired
    AddCourseStudentAdapter adapter;
    @Autowired
    LoadCoursePort loadCoursePort;

    @BeforeEach
    void init() {
        memberRepository.save(Member.builder()
                .name("student")
                .grade(11)
                .phoneNumber("test")
                .encryptedPassword("weqrew")
                .registeredDate(LocalDateTime.now())
                .role(Role.STUDENT)
                .build());
        memberRepository.save(Member.builder()
                .name("student")
                .grade(11)
                .phoneNumber("test1")
                .encryptedPassword("weqrew")
                .registeredDate(LocalDateTime.now())
                .role(Role.STUDENT)
                .build());
        Member teacher = memberRepository.save(Member.builder()
                .name("teacher")
                .grade(11)
                .phoneNumber("teacher1")
                .encryptedPassword("weqrew")
                .registeredDate(LocalDateTime.now())
                .role(Role.TEACHER)
                .build());
        courseRepository.save(new Course("testCourse", teacher));
    }

    @Test
    void Course_Student_추가_테스트() {
        final List<Student> students = memberRepository.findMembersByIdIsInAndRoleAndRemovedIsFalse(List.of(1l, 2l), Role.STUDENT)
                .stream()
                .map(member -> new Student(member.getId(), member.getName(), member.getGrade()))
                .toList();
        final com.hanpyeon.academyapi.course.domain.Course courseDomain = loadCoursePort.loadCourse(1l);

        courseDomain.addStudents(students);
        adapter.addToCourse(courseDomain);

        assertThat(courseStudentRepository.findAll().size())
                .isEqualTo(2);
        assertThat(courseRepository.findById(1l).orElseThrow().getStudents().size())
                .isEqualTo(2);
    }
}