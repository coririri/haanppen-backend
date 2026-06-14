package com.hanpyeon.academyapi.course.adapter.out;

import static org.assertj.core.api.Assertions.assertThat;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.security.Role;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CourseRepositoryTest {
    @Autowired
    EntityManager entityManager;
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CourseStudentRepository courseStudentRepository;
    @Autowired
    MemoRepository memoRepository;

    private Member student = Member.builder()
            .name("student")
            .grade(11)
            .phoneNumber("test")
            .encryptedPassword("weqrew")
            .registeredDate(LocalDateTime.now())
            .role(Role.STUDENT)
            .build();
    private Member student2 = Member.builder()
            .name("student")
            .grade(11)
            .phoneNumber("test1")
            .encryptedPassword("weqrew")
            .registeredDate(LocalDateTime.now())
            .role(Role.STUDENT)
            .build();
    private Member teacher = Member.builder()
            .name("teacher")
            .grade(11)
            .phoneNumber("teacher1")
            .encryptedPassword("weqrew")
            .registeredDate(LocalDateTime.now())
            .role(Role.TEACHER)
            .build();

    private Course course = new Course("test", teacher);

    void init() {
        memberRepository.save(student);
        memberRepository.save(student2);
        memberRepository.save(teacher);
        courseRepository.save(course);
        courseStudentRepository.save(CourseStudent.addToCourse(student, course));
        courseStudentRepository.save(CourseStudent.addToCourse(student2, course));
    }

    @Test
    void 선생님_번호를_통한_수업_조회_테스트() {
        init();
        assertThat(courseRepository.findAll().size())
                .isEqualTo(1);
    }
}