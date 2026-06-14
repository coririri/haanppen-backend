package com.hanpyeon.academyapi.course.domain;

import com.hanpyeon.academyapi.course.application.exception.IllegalCourseNameException;
import com.hanpyeon.academyapi.course.application.exception.IllegalCourseStudentStateException;
import com.hanpyeon.academyapi.course.application.exception.IllegalCourseStudentSizeException;
import com.hanpyeon.academyapi.course.application.exception.NotFoundTeacherException;
import com.hanpyeon.academyapi.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Course {
    private static final int MAX_STUDENT_SIZE = 50;
    private final Long courseId;
    private String courseName;
    private List<Student> students;
    private Teacher teacher;

    public void addStudents(final List<Student> students) {
        final List<Student> tempAddedStudents = new ArrayList<>(students);
        tempAddedStudents.addAll(this.students);

        final List<Student> newCourseStudents = tempAddedStudents.stream()
                .distinct().toList();

        validateStudents(newCourseStudents);
        this.students = newCourseStudents;
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

    public void changeCourseName(final String newCourseName) {
        validateCourseName(newCourseName);
        this.courseName = newCourseName;
    }

    public void changeTeacher(final Teacher newTeacher) {
        validateTeacher(newTeacher);
        this.teacher = newTeacher;
    }

    public static Course createNewCourse(final String courseName, final List<Student> students, final Teacher teacher) {
        final Course course = new Course(null, courseName, students, teacher);
        validate(course);
        return course;
    }
    public static Course loadByEntity(final Long courseId, final String courseName, final List<Student> students, final Teacher teacher) {
        final Course course = new Course(courseId, courseName, students, teacher);
        validate(course);
        return course;
    }

    private static void validate(Course course) {
        validateCourseName(course.getCourseName());
        validateStudents(course.getStudents());
        validateTeacher(course.getTeacher());
    }

    private static void validateCourseName(final String courseName) {
        if (Objects.isNull(courseName)) {
            throw new IllegalCourseNameException("반 이름은 null 일 수 없습니다", ErrorCode.ILLEGAL_COURSE_NAME);
        }
        if (courseName.length() > 100) {
            throw new IllegalCourseNameException("글자수 초과", ErrorCode.ILLEGAL_COURSE_NAME);
        }
    }

    private static void validateStudents(final List<Student> students) {
        if (Objects.isNull(students) || students.isEmpty()) {
            return;
        }
        if (students.stream().anyMatch(Objects::isNull)) {
            throw new IllegalCourseStudentStateException("등록 할 수 없는 학생이 포함", ErrorCode.ILLEGAL_COURSE_STUDENT_STATE);
        }
        validateStudentSize(students.size());
    }

    private static void validateStudentSize(final Integer size) {
        if (size > MAX_STUDENT_SIZE) {
            throw new IllegalCourseStudentSizeException("최대인원 : " + MAX_STUDENT_SIZE + "현재원 : " + size, ErrorCode.ILLEGAL_COURSE_STUDENT_SIZE);
        }
    }

    private static void validateTeacher(final Teacher teacher) {
        if (Objects.isNull(teacher)) {
            throw new NotFoundTeacherException("선생님 부재", ErrorCode.ILLEGAL_COURSE_STUDENT_STATE);
        }
    }
}
