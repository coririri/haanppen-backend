package com.hanpyeon.academyapi.course.adapter.out;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.security.Role;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class MemoRepositoryTest {
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

        final LocalDate today = LocalDate.now();
        final LocalDate startDayOfMonth = today.withDayOfMonth(1);
        final LocalDate endDayOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        student = memberRepository.findById(student.getId())
                .orElseThrow();
        course = courseRepository.findById(course.getId())
                .orElseThrow();
        final Long studentId = student.getId();
        memoRepository.save(new Memo(course, today, "test", "test"));
        memoRepository.save(new Memo(course, today, "test", "test"));
        memoRepository.save(new Memo(course, today, "test", "test"));
        memoRepository.save(new Memo(course, LocalDate.MAX, "test", "test"));
        memoRepository.save(new Memo(course, LocalDate.MAX, "test", "test"));

        entityManager.flush();
        entityManager.clear();

        Assertions.assertEquals(memoRepository.findAllByMonthAndStudentId(
                        startDayOfMonth, endDayOfMonth, studentId).size(),
                3);
    }
}