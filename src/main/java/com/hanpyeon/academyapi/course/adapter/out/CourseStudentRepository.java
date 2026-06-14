package com.hanpyeon.academyapi.course.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface CourseStudentRepository extends JpaRepository<CourseStudent, Long> {
    List<CourseStudent> findCourseStudentByCourseEntity(final Course course);
    List<CourseStudent> findCourseStudentsByMemberId(final Long memberId);
    void deleteCourseStudentByCourseEntityId(final Long courseId);
}
