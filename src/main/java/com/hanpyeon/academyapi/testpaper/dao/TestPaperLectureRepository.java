package com.hanpyeon.academyapi.testpaper.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TestPaperLectureRepository extends JpaRepository<TestPaperLecture, Long> {

    @Query("SELECT tpl FROM TestPaperLecture tpl JOIN FETCH tpl.testPaper WHERE tpl.id = :lectureId")
    Optional<TestPaperLecture> findByIdWithTestPaper(@Param("lectureId") Long lectureId);

    @Query("SELECT tpl FROM TestPaperLecture tpl WHERE tpl.testPaper.id = :testPaperId")
    Optional<TestPaperLecture> findByTestPaperId(@Param("testPaperId") Long testPaperId);

    boolean existsByTestPaperId(Long testPaperId);
}
