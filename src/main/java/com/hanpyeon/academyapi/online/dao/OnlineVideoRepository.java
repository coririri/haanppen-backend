package com.hanpyeon.academyapi.online.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OnlineVideoRepository extends JpaRepository<OnlineVideo, Long> {
    @Modifying
    @Query("UPDATE OnlineVideo ov SET ov.videoSequence = ov.videoSequence - 1 WHERE ov.videoSequence > :videoSequence AND ov.onlineCourse.id = :onlineCourseId")
    void updateUpperVideosSequence(@Param("videoSequence") final Integer videoSequence, @Param("onlineCourseId") final Long onlineCourseId);

    @Query("SELECT ov FROM OnlineVideo ov JOIN FETCH ov.onlineCourse JOIN FETCH ov.onlineCourse.teacher WHERE ov.id = :onlineVideoId")
    Optional<OnlineVideo> loadSingleOnlineVideoWithCourseWithTeacherByVideoId(@Param("onlineVideoId") final Long onlineVideoId);
}
