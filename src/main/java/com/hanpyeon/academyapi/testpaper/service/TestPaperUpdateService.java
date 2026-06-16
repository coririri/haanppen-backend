package com.hanpyeon.academyapi.testpaper.service;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.account.repository.MemberRepository;
import com.hanpyeon.academyapi.security.Role;
import com.hanpyeon.academyapi.testpaper.dao.TestPaper;
import com.hanpyeon.academyapi.testpaper.dao.TestPaperRepository;
import com.hanpyeon.academyapi.testpaper.dao.TestPaperStudent;
import com.hanpyeon.academyapi.testpaper.dao.TestPaperStudentRepository;
import com.hanpyeon.academyapi.testpaper.dto.TestPaperUpdateDto;
import com.hanpyeon.academyapi.testpaper.dto.UpdateTestPaperStudentsDto;
import com.hanpyeon.academyapi.testpaper.exception.NoSuchTestPaperException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestPaperUpdateService {
    private final TestPaperRepository testPaperRepository;
    private final TestPaperStudentRepository testPaperStudentRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void updateTestPaper(final TestPaperUpdateDto dto) {
        validatePermission(dto.role());

        final TestPaper testPaper = testPaperRepository.findById(dto.testPaperId())
                .orElseThrow(() -> new NoSuchTestPaperException("시험지 반을 찾을 수 없습니다", ErrorCode.NO_SUCH_TEST_PAPER));

        if (dto.testPaperName() != null) {
            testPaper.changeTestPaperName(dto.testPaperName());
        }

        if (dto.teacherId() != null) {
            final Member newTeacher = memberRepository.findById(dto.teacherId())
                    .orElseThrow(() -> new NoSuchTestPaperException("선생님을 찾을 수 없습니다", ErrorCode.NOT_FOUND_TEACHER));
            testPaper.changeTeacher(newTeacher);
        }
    }

    @Transactional
    public void updateTestPaperStudents(final UpdateTestPaperStudentsDto dto) {
        validatePermission(dto.role());

        final TestPaper testPaper = testPaperRepository.findById(dto.testPaperId())
                .orElseThrow(() -> new NoSuchTestPaperException("시험지 반을 찾을 수 없습니다", ErrorCode.NO_SUCH_TEST_PAPER));

        testPaperStudentRepository.deleteByTestPaperId(dto.testPaperId());

        if (dto.students() != null && !dto.students().isEmpty()) {
            final List<Member> students = memberRepository.findAllById(dto.students());
            final List<TestPaperStudent> testPaperStudents = students.stream()
                    .map(student -> TestPaperStudent.addToTestPaper(student, testPaper))
                    .toList();
            testPaperStudentRepository.saveAll(testPaperStudents);
        }
    }

    @Transactional
    public void deleteTestPaper(final Long testPaperId, final Role role) {
        validatePermission(role);

        final TestPaper testPaper = testPaperRepository.findById(testPaperId)
                .orElseThrow(() -> new NoSuchTestPaperException("시험지 반을 찾을 수 없습니다", ErrorCode.NO_SUCH_TEST_PAPER));

        testPaperStudentRepository.deleteByTestPaperId(testPaperId);
        testPaperRepository.delete(testPaper);
    }

    private void validatePermission(final Role role) {
        if (role == Role.STUDENT) {
            throw new NoSuchTestPaperException("학생은 시험지 반을 수정/삭제할 수 없습니다", ErrorCode.INVALID_TEST_PAPER_ACCESS);
        }
    }
}
