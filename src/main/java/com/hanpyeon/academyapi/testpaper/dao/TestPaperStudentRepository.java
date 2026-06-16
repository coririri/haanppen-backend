package com.hanpyeon.academyapi.testpaper.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TestPaperStudentRepository extends JpaRepository<TestPaperStudent, Long> {

    @Query("SELECT tps FROM TestPaperStudent tps " +
           "JOIN FETCH tps.member m " +
           "WHERE tps.testPaper.id = :testPaperId AND m.removed = false")
    List<TestPaperStudent> findByTestPaperId(@Param("testPaperId") Long testPaperId);

    @Query("SELECT tps FROM TestPaperStudent tps " +
           "JOIN FETCH tps.testPaper tp " +
           "WHERE tps.member.id = :studentId")
    List<TestPaperStudent> findByStudentId(@Param("studentId") Long studentId);

    @Modifying
    @Query("DELETE FROM TestPaperStudent tps WHERE tps.testPaper.id = :testPaperId")
    void deleteByTestPaperId(@Param("testPaperId") Long testPaperId);

    @Modifying
    @Query("DELETE FROM TestPaperStudent tps WHERE tps.testPaper.id = :testPaperId AND tps.member.id IN :studentIds")
    void deleteByTestPaperIdAndStudentIds(@Param("testPaperId") Long testPaperId, @Param("studentIds") List<Long> studentIds);
}
