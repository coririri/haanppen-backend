package com.hanpyeon.academyapi.testpaper.domain;

import com.hanpyeon.academyapi.testpaper.exception.IllegalTestPaperNameException;
import com.hanpyeon.academyapi.testpaper.exception.IllegalTestPaperStudentSizeException;
import com.hanpyeon.academyapi.testpaper.exception.IllegalTestPaperStudentStateException;
import com.hanpyeon.academyapi.testpaper.exception.NotFoundTeacherException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class TestPaperDomain {
    private static final int MAX_STUDENT_SIZE = 50;
    private final Long testPaperId;
    private String testPaperName;
    private List<Student> students;
    private Teacher teacher;

    public void addStudents(final List<Student> students) {
        final List<Student> tempAddedStudents = new ArrayList<>(students);
        tempAddedStudents.addAll(this.students);

        final List<Student> newTestPaperStudents = tempAddedStudents.stream()
                .distinct().toList();

        validateStudents(newTestPaperStudents);
        this.students = newTestPaperStudents;
    }

    public void setStudents(final List<Student> students) {
        validateStudents(students);
        this.students = students;
    }

    public void removeStudents(final List<Student> students) {
        final List<Student> tempStudents = new ArrayList<>(this.students);
        tempStudents.removeAll(students);

        validateStudents(tempStudents);
        this.students = tempStudents;
    }

    public void changeTestPaperName(final String newTestPaperName) {
        validateTestPaperName(newTestPaperName);
        this.testPaperName = newTestPaperName;
    }

    public void changeTeacher(final Teacher newTeacher) {
        validateTeacher(newTeacher);
        this.teacher = newTeacher;
    }

    public static TestPaperDomain createNew(final String testPaperName, final List<Student> students, final Teacher teacher) {
        final TestPaperDomain testPaper = new TestPaperDomain(null, testPaperName, students, teacher);
        validate(testPaper);
        return testPaper;
    }

    public static TestPaperDomain loadByEntity(final Long testPaperId, final String testPaperName, final List<Student> students, final Teacher teacher) {
        final TestPaperDomain testPaper = new TestPaperDomain(testPaperId, testPaperName, students, teacher);
        validate(testPaper);
        return testPaper;
    }

    private static void validate(TestPaperDomain testPaper) {
        validateTestPaperName(testPaper.getTestPaperName());
        validateStudents(testPaper.getStudents());
        validateTeacher(testPaper.getTeacher());
    }

    private static void validateTestPaperName(final String testPaperName) {
        if (Objects.isNull(testPaperName)) {
            throw new IllegalTestPaperNameException("반 이름은 null 일 수 없습니다", ErrorCode.ILLEGAL_TEST_PAPER_NAME);
        }
        if (testPaperName.length() > 100) {
            throw new IllegalTestPaperNameException("글자수 초과", ErrorCode.ILLEGAL_TEST_PAPER_NAME);
        }
    }

    private static void validateStudents(final List<Student> students) {
        if (Objects.isNull(students) || students.isEmpty()) {
            return;
        }
        if (students.stream().anyMatch(Objects::isNull)) {
            throw new IllegalTestPaperStudentStateException("등록 할 수 없는 학생이 포함", ErrorCode.ILLEGAL_TEST_PAPER_STUDENT_STATE);
        }
        validateStudentSize(students.size());
    }

    private static void validateStudentSize(final Integer size) {
        if (size > MAX_STUDENT_SIZE) {
            throw new IllegalTestPaperStudentSizeException("최대인원 : " + MAX_STUDENT_SIZE + " 현재인원 : " + size, ErrorCode.ILLEGAL_TEST_PAPER_STUDENT_SIZE);
        }
    }

    private static void validateTeacher(final Teacher teacher) {
        if (Objects.isNull(teacher)) {
            throw new NotFoundTeacherException("선생님 부재", ErrorCode.NOT_FOUND_TEACHER);
        }
    }
}
