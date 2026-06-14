package com.hanpyeon.academyapi.course.adapter.out;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByTeacherId(final Long teacherId);
}
