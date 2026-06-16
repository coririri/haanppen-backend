package com.hanpyeon.academyapi.testpaper.service;

import com.hanpyeon.academyapi.account.entity.Member;
import com.hanpyeon.academyapi.security.Role;
import com.hanpyeon.academyapi.testpaper.dao.TestPaper;
import com.hanpyeon.academyapi.testpaper.dao.TestPaperRepository;
import com.hanpyeon.academyapi.testpaper.dao.TestPaperStudent;
import com.hanpyeon.academyapi.testpaper.dao.TestPaperStudentRepository;
import com.hanpyeon.academyapi.testpaper.dto.StudentPreview;
import com.hanpyeon.academyapi.testpaper.dto.TestPaperDetails;
import com.hanpyeon.academyapi.testpaper.dto.TestPaperPreview;
import com.hanpyeon.academyapi.testpaper.exception.NoSuchTestPaperException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TestPaperQueryService {
    private final TestPaperRepository testPaperRepository;
    private final TestPaperStudentRepository testPaperStudentRepository;

    public List<TestPaperPreview> getAllTestPapers() {
        return testPaperRepository.findAll().stream()
                .map(this::toPreview)
                .collect(Collectors.toList());
    }

    public List<TestPaperPreview> getTestPapersByTeacherId(final Long teacherId) {
        return testPaperRepository.findByTeacherId(teacherId).stream()
                .map(this::toPreview)
                .collect(Collectors.toList());
    }

    public List<TestPaperPreview> getTestPapersByStudentId(final Long studentId) {
        return testPaperStudentRepository.findByStudentId(studentId).stream()
                .map(TestPaperStudent::getTestPaper)
                .map(this::toPreview)
                .collect(Collectors.toList());
    }

    public TestPaperDetails getTestPaperDetails(final Long testPaperId) {
        final TestPaper testPaper = testPaperRepository.findByIdWithStudents(testPaperId)
                .orElseThrow(() -> new NoSuchTestPaperException("시험지 반을 찾을 수 없습니다", ErrorCode.NO_SUCH_TEST_PAPER));

        final List<StudentPreview> students = testPaper.getTestPaperStudents().stream()
                .filter(tps -> tps.getMember() != null && !tps.getMember().getRemoved())
                .map(tps -> toStudentPreview(tps.getMember()))
                .collect(Collectors.toList());

        return new TestPaperDetails(
                testPaper.getId(),
                testPaper.getTestPaperName(),
                testPaper.getTeacher().getName(),
                testPaper.getTeacher().getId(),
                students
        );
    }

    public List<TestPaperPreview> getTestPapersByMemberId(final Long memberId, final Role role) {
        return switch (role) {
            case ADMIN, MANAGER -> getAllTestPapers();
            case TEACHER -> getTestPapersByTeacherId(memberId);
            case STUDENT -> getTestPapersByStudentId(memberId);
        };
    }

    private TestPaperPreview toPreview(final TestPaper testPaper) {
        final long studentCount = testPaper.getTestPaperStudents().stream()
                .filter(tps -> tps.getMember() != null && !tps.getMember().getRemoved())
                .count();

        return new TestPaperPreview(
                testPaper.getId(),
                testPaper.getTestPaperName(),
                testPaper.getTeacher().getName(),
                testPaper.getTeacher().getId(),
                (int) studentCount
        );
    }

    private StudentPreview toStudentPreview(final Member member) {
        return new StudentPreview(
                member.getId(),
                member.getName(),
                member.getGrade()
        );
    }
}
