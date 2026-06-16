package com.hanpyeon.academyapi.testpaper.service;

import com.hanpyeon.academyapi.dir.dto.FileView;
import com.hanpyeon.academyapi.dir.dto.QueryDirectoryDto;
import com.hanpyeon.academyapi.dir.service.query.DirectoryQueryService;
import com.hanpyeon.academyapi.security.Role;
import com.hanpyeon.academyapi.testpaper.dao.TestPaper;
import com.hanpyeon.academyapi.testpaper.dao.TestPaperLecture;
import com.hanpyeon.academyapi.testpaper.dao.TestPaperLectureRepository;
import com.hanpyeon.academyapi.testpaper.dao.TestPaperRepository;
import com.hanpyeon.academyapi.testpaper.dto.TestPaperLectureCreateDto;
import com.hanpyeon.academyapi.testpaper.dto.TestPaperLectureResponse;
import com.hanpyeon.academyapi.testpaper.dto.TestPaperLectureUpdateDto;
import com.hanpyeon.academyapi.testpaper.exception.NoSuchTestPaperException;
import com.hanpyeon.academyapi.testpaper.exception.TestPaperLectureAlreadyExistsException;
import com.hanpyeon.academyapi.testpaper.exception.NoSuchTestPaperLectureException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TestPaperLectureService {
    private final TestPaperLectureRepository testPaperLectureRepository;
    private final TestPaperRepository testPaperRepository;
    private final DirectoryQueryService directoryQueryService;

    @Transactional
    public Long createLecture(final TestPaperLectureCreateDto dto) {
        validatePermission(dto.role());

        final TestPaper testPaper = testPaperRepository.findById(dto.testPaperId())
                .orElseThrow(() -> new NoSuchTestPaperException("시험지 반을 찾을 수 없습니다", ErrorCode.NO_SUCH_TEST_PAPER));

        if (testPaperLectureRepository.existsByTestPaperId(dto.testPaperId())) {
            throw new TestPaperLectureAlreadyExistsException("이미 문제집 강의가 존재합니다", ErrorCode.TEST_PAPER_LECTURE_ALREADY_EXISTS);
        }

        final TestPaperLecture lecture = new TestPaperLecture(testPaper, dto.lectureName());
        if (dto.description() != null) {
            lecture.updateDescription(dto.description());
        }
        if (dto.directoryPath() != null) {
            lecture.updateDirectoryPath(dto.directoryPath());
        }

        final TestPaperLecture savedLecture = testPaperLectureRepository.save(lecture);
        return savedLecture.getId();
    }

    @Transactional(readOnly = true)
    public TestPaperLectureResponse getLectureById(final Long lectureId) {
        final TestPaperLecture lecture = testPaperLectureRepository.findByIdWithTestPaper(lectureId)
                .orElseThrow(() -> new NoSuchTestPaperLectureException("문제집 강의를 찾을 수 없습니다", ErrorCode.NO_SUCH_TEST_PAPER_LECTURE));

        return toResponse(lecture);
    }

    @Transactional(readOnly = true)
    public TestPaperLectureResponse getLectureByTestPaperId(final Long testPaperId) {
        final TestPaperLecture lecture = testPaperLectureRepository.findByTestPaperId(testPaperId)
                .orElseThrow(() -> new NoSuchTestPaperLectureException("문제집 강의를 찾을 수 없습니다", ErrorCode.NO_SUCH_TEST_PAPER_LECTURE));

        return toResponse(lecture);
    }

    @Transactional
    public void updateLecture(final TestPaperLectureUpdateDto dto) {
        validatePermission(dto.role());

        final TestPaperLecture lecture = testPaperLectureRepository.findById(dto.lectureId())
                .orElseThrow(() -> new NoSuchTestPaperLectureException("문제집 강의를 찾을 수 없습니다", ErrorCode.NO_SUCH_TEST_PAPER_LECTURE));

        if (dto.lectureName() != null) {
            lecture.updateLectureName(dto.lectureName());
        }
        if (dto.description() != null) {
            lecture.updateDescription(dto.description());
        }
        if (dto.directoryPath() != null) {
            lecture.updateDirectoryPath(dto.directoryPath());
        }
    }

    @Transactional
    public void deleteLecture(final Long lectureId, final Role role) {
        validatePermission(role);

        final TestPaperLecture lecture = testPaperLectureRepository.findById(lectureId)
                .orElseThrow(() -> new NoSuchTestPaperLectureException("문제집 강의를 찾을 수 없습니다", ErrorCode.NO_SUCH_TEST_PAPER_LECTURE));

        testPaperLectureRepository.delete(lecture);
    }

    private void validatePermission(final Role role) {
        if (role == Role.STUDENT) {
            throw new NoSuchTestPaperException("학생은 문제집 강의를 관리할 수 없습니다", ErrorCode.INVALID_TEST_PAPER_ACCESS);
        }
    }

    private TestPaperLectureResponse toResponse(final TestPaperLecture lecture) {
        List<FileView> videos = Collections.emptyList();

        if (lecture.getDirectoryPath() != null && !lecture.getDirectoryPath().isBlank()) {
            try {
                // 문제집 강의는 해당 반에 속한 학생도 영상을 볼 수 있어야 하므로 권한 체크 없이 조회
                videos = directoryQueryService.queryDirectoryWithoutPermissionCheck(lecture.getDirectoryPath());
            } catch (Exception e) {
                // 디렉토리 조회 실패 시 빈 리스트 반환
                videos = Collections.emptyList();
            }
        }

        return new TestPaperLectureResponse(
                lecture.getId(),
                lecture.getTestPaper().getId(),
                lecture.getTestPaper().getTestPaperName(),
                lecture.getLectureName(),
                lecture.getDescription(),
                lecture.getDirectoryPath(),
                lecture.getCreatedTime(),
                videos
        );
    }
}
