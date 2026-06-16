package com.hanpyeon.academyapi.testpaper.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TestPaperRepository extends JpaRepository<TestPaper, Long> {

    @Query("SELECT tp FROM TEST_PAPER tp JOIN FETCH tp.teacher WHERE tp.id = :testPaperId")
    Optional<TestPaper> findByIdWithTeacher(@Param("testPaperId") Long testPaperId);

    @Query("SELECT tp FROM TEST_PAPER tp JOIN FETCH tp.teacher WHERE tp.teacher.id = :teacherId")
    List<TestPaper> findByTeacherId(@Param("teacherId") Long teacherId);

    @Query("SELECT DISTINCT tp FROM TEST_PAPER tp " +
           "LEFT JOIN FETCH tp.students tps " +
           "LEFT JOIN FETCH tps.member " +
           "WHERE tp.id = :testPaperId")
    Optional<TestPaper> findByIdWithStudents(@Param("testPaperId") Long testPaperId);
}
