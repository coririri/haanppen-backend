package com.hanpyeon.academyapi.course.adapter.out;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.course.application.DeleteCourseService;
import com.hanpyeon.academyapi.course.application.dto.DeleteCourseCommand;
import com.hanpyeon.academyapi.course.application.port.in.DeleteCourseUseCase;
import com.hanpyeon.academyapi.security.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@ActiveProfiles("test")
@Import({CourseMapper.class, LoadCourseAdapter.class, DeleteCourseAdapter.class, DeleteCourseStudentAdapter.class, DeleteCourseService.class})
class DeleteCourseServiceTest {

    @Autowired
    DeleteCourseUseCase deleteCourseUseCase;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CourseStudentRepository courseStudentRepository;
    @Autowired
    CourseRepository courseRepository;

    @BeforeEach
    void init() {
        // 학생 초기화
        memberRepository.save(Member.builder()
                .role(Role.STUDENT)
                .name("test")
                .grade(11)
                .phoneNumber("123123")
                .registeredDate(LocalDateTime.now())
                .encryptedPassword("test")
                .build());
        memberRepository.save(Member.builder()
                .role(Role.STUDENT)
                .name("test1")
                .grade(11)
                .phoneNumber("123")
                .registeredDate(LocalDateTime.now())
                .encryptedPassword("test")
                .build());

        // 선생 초기화
        memberRepository.save(Member.builder()
                .role(Role.TEACHER)
                .name("test21")
                .phoneNumber("1213")
                .registeredDate(LocalDateTime.now())
                .encryptedPassword("test")
                .build());

        // 수업 등록
        Member teacher = memberRepository.findMemberByPhoneNumberAndRemovedIsFalse("1213").orElseThrow();
        Course course = courseRepository.save(new Course("테스트용 수업이름", teacher));

        // 수업에 학생 등록
        Member student1 = memberRepository.findMemberByPhoneNumberAndRemovedIsFalse("123123").orElseThrow();
        Member student2 = memberRepository.findMemberByPhoneNumberAndRemovedIsFalse("123").orElseThrow();
        courseStudentRepository.save(CourseStudent.addToCourse(student1, course));
        courseStudentRepository.save(CourseStudent.addToCourse(student2, course));
    }

    @Test
    @Transactional
    void test() {
        List<Course> courses = courseRepository.findAll();
        deleteCourseUseCase.delete(new DeleteCourseCommand(courses.get(0).getId(), Role.ADMIN, 1L));
        assertThat(courseStudentRepository.count())
                .isEqualTo(0);
        assertThat(courseRepository.count())
                .isEqualTo(0);
        assertThat(memberRepository.count())
                .isEqualTo(3);
    }
}