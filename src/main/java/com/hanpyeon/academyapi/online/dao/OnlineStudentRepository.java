package com.hanpyeon.academyapi.online.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OnlineStudentRepository extends JpaRepository<OnlineStudent, Long> {
    @Modifying
    @Query("DELETE FROM OnlineStudent o WHERE o.course.id = :onlineCourseId")
    void removeAllByOnlineCourseId(@Param("onlineCourseId") Long onlineCourseId);

    @Query("SELECT os FROM OnlineStudent os JOIN FETCH OnlineCourse oc ON os.course.id = oc.id WHERE os.member.id = :onlineStudentId")
    OnlineStudent findOnlineStudent(@Param("onlineStudentId") final Long onlineStudentId);
}
