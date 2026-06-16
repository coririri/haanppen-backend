package com.hanpyeon.academyapi.testpaper.service;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.security.Role;
import com.hanpyeon.academyapi.testpaper.dao.TestPaper;
import com.hanpyeon.academyapi.testpaper.dao.TestPaperRepository;
import com.hanpyeon.academyapi.testpaper.dao.TestPaperStudent;
import com.hanpyeon.academyapi.testpaper.dao.TestPaperStudentRepository;
import com.hanpyeon.academyapi.testpaper.dto.TestPaperRegisterDto;
import com.hanpyeon.academyapi.testpaper.exception.NoSuchTestPaperException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestPaperRegisterService {
    private final TestPaperRepository testPaperRepository;
    private final TestPaperStudentRepository testPaperStudentRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long register(final TestPaperRegisterDto dto) {
        validatePermission(dto.role());

        final Member teacher = memberRepository.findById(dto.teacherId())
                .orElseThrow(() -> new NoSuchTestPaperException("선생님을 찾을 수 없습니다", ErrorCode.NOT_FOUND_TEACHER));

        final TestPaper testPaper = new TestPaper(dto.testPaperName(), teacher);
        final TestPaper savedTestPaper = testPaperRepository.save(testPaper);

        if (dto.students() != null && !dto.students().isEmpty()) {
            addStudentsToTestPaper(savedTestPaper, dto.students());
        }

        return savedTestPaper.getId();
    }

    private void addStudentsToTestPaper(final TestPaper testPaper, final List<Long> studentIds) {
        final List<Member> students = memberRepository.findAllById(studentIds);
        final List<TestPaperStudent> testPaperStudents = students.stream()
                .map(student -> TestPaperStudent.addToTestPaper(student, testPaper))
                .toList();
        testPaperStudentRepository.saveAll(testPaperStudents);
    }

    private void validatePermission(final Role role) {
        if (role == Role.STUDENT) {
            throw new NoSuchTestPaperException("학생은 시험지 반을 등록할 수 없습니다", ErrorCode.INVALID_TEST_PAPER_ACCESS);
        }
    }
}
